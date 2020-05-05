package app.utils;

import app.models.entities.Item;
import app.models.entities.Person;
import app.models.entities.Role;
import app.models.entities.Warehouse;
import app.models.entities.repositories.ItemRepository;
import app.models.entities.repositories.PersonRepository;
import app.models.entities.repositories.RoleRepository;
import app.models.entities.repositories.WarehouseRepository;
import app.models.requests.AddItemRequest;
import app.models.requests.LoginRequest;
import app.models.requests.UpdateItemRequest;
import app.models.responses.*;
import app.models.requests.RegistrationRequest;
import app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class MobileServiceImpl implements MobileService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity register(RegistrationRequest registrationRequest) {
        if(personRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        } else {
            try {
                Role role = roleRepository.findByName("ROLE_USER").get();
                MultipartFile multipartFile = registrationRequest.getDisplayPicture();
                Byte[] picture = (multipartFile != null)? primToWrap(multipartFile.getBytes()) : null;
                Person person = personRepository.save(new Person(registrationRequest.getUsername(), registrationRequest.getFirstName(),
                        registrationRequest.getLastName(), registrationRequest.geteMail(), registrationRequest.getPassword(),
                        picture, Collections.singleton(role), Collections.emptyList(), Collections.emptyList()));
                UserDetails userDetails = userDetailsService.loadUserByUsername(person.getUsername());
                String token = jwtUtil.generateToken(userDetails);
                String ownerUrl = buildUserDpUrl(person);
                return ResponseEntity.ok(new RegisteredUser(person.getUsername(), person.getFirstName(),
                        person.getLastName(), person.geteMail(), token, ownerUrl));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something wrong with your request", e);
            }
        }
    }

    @Override
    public ResponseEntity login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Username or password");
        }
        Person person = personRepository.findByUsername(loginRequest.getUsername()).get();
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        String ownerUrl = buildUserDpUrl(person);
        return ResponseEntity.ok(new RegisteredUser(person.getUsername(), person.getFirstName(),
                person.getLastName(), person.geteMail(), token, ownerUrl));
    }

    @Override
    public List<WarehouseResponse> getWarehouses(Principal principal, boolean mine) {
        Person person = personRepository.findByUsername(principal.getName()).get();
        List<WarehouseResponse> warehouses = new ArrayList<>();
        Iterable<Warehouse> temp = mine? person.getWarehouses() : warehouseRepository.findAll();
        temp.forEach(w -> {
            String warehouseUrl = buildWarehousePictureUrl(w);
            PersonResponse owner = null;
            String ownerUrl = null;
            if (w.getOwner() != null) {
                ownerUrl = buildUserDpUrl(w.getOwner());
                owner = new PersonResponse(w.getOwner().getUsername(), w.getOwner().getFirstName(), w.getOwner().getLastName(),
                        w.getOwner().geteMail(), ownerUrl);
            }
            warehouses.add(new WarehouseResponse(w.getName(), w.getAddress(), w.getUuid(), warehouseUrl, owner));
        });
        return warehouses;
    }

    @Override
    public WarehouseResponse getSpecificWarehouse(String warehouseName) {
        try {
            Warehouse warehouse = warehouseRepository.findByName(warehouseName).orElseThrow(() ->
                    new Exception("Warehouse doesn't exist"));
            Person p = warehouse.getOwner();
            String warehouseUrl = buildWarehousePictureUrl(warehouse);
            String ownerUrl = buildUserDpUrl(p);
            return new WarehouseResponse(warehouse.getName(), warehouse.getAddress(), warehouse.getUuid(), warehouseUrl,
                    new PersonResponse(p.getUsername(), p.getFirstName(),p.getLastName(), p.geteMail(), ownerUrl));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Warehouse doesn't exist");
        }
    }

    @Override
    public List<ItemResponse> getAllItemsForUser(Principal principal, String warehouseName) {
        Person person = personRepository.findByUsername(principal.getName()).get();
        try {
            Warehouse warehouse = warehouseRepository.findByName(warehouseName).orElseThrow(()->
                    new Exception("Warehouse doesn't exist!"));
            if(warehouse.getUsers().stream().noneMatch(user -> user.getUsername().equals(person.getUsername()))) {
                throw new Exception("You don't have permission!");
            }
            List<ItemResponse> items = new ArrayList<>();
            //TODO Definitely a faster/more efficient/less code way
            List<Item> filtered = warehouse.getItems().stream()
                    .filter(item -> item.getOwner().getUsername().equals(person.getUsername()))
                    .collect(Collectors.toList());
            filtered.forEach(item -> {
                String itemUrl = buildItemPictureUrl(item);
                items.add(new ItemResponse(item.getUuid(), item.getName(), item.getQuantity(), item.getUnitPrice(),
                        item.getCurrencyCode(), itemUrl));
            });
            return items;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ItemResponse addItemForUser(Principal principal, String warehouseName ,AddItemRequest addItemRequest) {
        Person person = personRepository.findByUsername(principal.getName()).get();
        try {
            Warehouse warehouse = warehouseRepository.findByName(warehouseName).orElseThrow(() ->
                    new Exception("Warehouse doesn't exist!"));
            if(warehouse.getUsers().stream().noneMatch(user -> user.getUsername().equals(person.getUsername()))) {
                throw new Exception("You don't have permission!");
            }
            Item saved = itemRepository.save(new Item(person, warehouse, addItemRequest.getName(), addItemRequest.getQuantity(),
                    addItemRequest.getUnitPrice(), addItemRequest.getCurrencyCode(), primToWrap(addItemRequest.getPicture().getBytes())));
            String itemUrl = buildItemPictureUrl(saved);
            return new ItemResponse(saved.getUuid(), saved.getName(), saved.getQuantity(), saved.getUnitPrice(),
                    saved.getCurrencyCode(), itemUrl);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ItemResponse getItemForUser(Principal principal, String warehouseName, String uuid) {
        Person person = personRepository.findByUsername(principal.getName()).get();
        try {
            Item item = itemRepository.findByUuid(uuid).orElseThrow(() ->
                    new Exception("Item Doesn't Exist!"));
            String itemUrl = buildItemPictureUrl(item);
            return new ItemResponse(item.getUuid(), item.getName(), item.getQuantity(), item.getUnitPrice(), item.getCurrencyCode(), itemUrl);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ItemResponse updateItemForUser(Principal principal, String warehouseName, String uuid, UpdateItemRequest updateItemRequest) {
        Person person = personRepository.findByUsername(principal.getName()).get();
        try {
            Item oldItem = itemRepository.findByUuid(uuid).orElseThrow(() ->
                    new Exception("Item Doesn't Exist!"));
            oldItem.setName(updateItemRequest.getName());
            oldItem.setCurrencyCode(updateItemRequest.getCurrencyCode());
            oldItem.setQuantity(updateItemRequest.getQuantity());
            oldItem.setUnitPrice(updateItemRequest.getUnitPrice());
            oldItem.setPicture((updateItemRequest.getPicture() != null)? primToWrap(updateItemRequest.getPicture().getBytes()) : new Byte[]{});
            Item newItem = itemRepository.save(oldItem);
            String itemUrl = ServletUriComponentsBuilder.fromCurrentRequest().path("/picture").toUriString();
            return new ItemResponse(newItem.getUuid(), newItem.getName(), newItem.getQuantity(), newItem.getUnitPrice(),
                    newItem.getCurrencyCode(), itemUrl);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity deleteItemForUser(Principal principal, String warehouseName, String uuid) {
        Person person = personRepository.findByUsername(principal.getName()).get();
        try {
            Item item = itemRepository.findByUuid(uuid).orElseThrow(
                    () -> new Exception("Item doesn't exist"));
            itemRepository.delete(item);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public List<PersonResponse> getAllUsersAdmin(Principal principal) {
        List<PersonResponse> people = new ArrayList<>();
        personRepository.findAll().forEach(person -> {
            String userUrl = buildUserDpUrl(person);
            people.add(new PersonResponse(person.getUsername(), person.getFirstName(), person.getLastName(), person.geteMail(), userUrl));
        });
        return people;
    }

    @Override
    public PersonResponse getUserAdmin(Principal principal, String username) {
        try {
            Person person = personRepository.findByUsername(username).orElseThrow(()->
                    new Exception("User doesn't exist!"));
            String userUrl = buildUserDpUrl(person);
            return new PersonResponse(person.getUsername(), person.getFirstName(), person.getLastName(), person.geteMail(), userUrl);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ByteArrayResource> getUserPicture(String username) {
        try {
            Person person = personRepository.findByUsername(username).orElseThrow(()->
                    new Exception("User doesn't exist!"));
            byte[] bytes = (person.getDisplayPicture() != null) ? wrapToPrim(person.getDisplayPicture()) : new byte[]{};
            ByteArrayResource picture = new ByteArrayResource(bytes);
            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .headers(headers)
                    .body(picture);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ByteArrayResource> getItemPicture(String warehouseName, String uuid) {
        try {
            Item item = itemRepository.findByUuid(uuid).orElseThrow(()->
                    new Exception("Item doesn't exist!"));
            byte[] bytes = (item.getPicture() != null) ? wrapToPrim(item.getPicture()) : new byte[]{};
            ByteArrayResource picture = new ByteArrayResource(bytes);
            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(picture);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ByteArrayResource> getWarehousePicture(String warehouseName) {
        try {
            Warehouse warehouse = warehouseRepository.findByName(warehouseName).orElseThrow(()->
                    new Exception("Warehouse doesn't exist!"));
            byte[] bytes = (warehouse.getPicture() != null) ? wrapToPrim(warehouse.getPicture()) : new byte[]{};
            ByteArrayResource picture = new ByteArrayResource(bytes);
            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(picture);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity updateUserPicture(String username, MultipartFile dp) {
        try {
            Person person = personRepository.findByUsername(username).orElseThrow(() ->
                    new Exception("User doesn't exist!"));
            Byte[] picture = (dp != null) ? primToWrap(dp.getBytes()) : null;
            person.setDisplayPicture(picture);
            personRepository.save(person);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity updateItemPicture(String warehouseName, String uuid, MultipartFile picture) {
        try {
            Item item = itemRepository.findByUuid(uuid).orElseThrow(() ->
                    new Exception("Item doesn't exist!"));
            Byte[] pic = (picture != null) ? primToWrap(picture.getBytes()) : null;
            item.setPicture(pic);
            itemRepository.save(item);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity updateWarehousePicture(String warehouseName, MultipartFile picture) {
        try {
            Warehouse warehouse = warehouseRepository.findByName(warehouseName).orElseThrow(() ->
                    new Exception("Warehouse doesn't exist!"));
            Byte[] pic = (picture != null) ? primToWrap(picture.getBytes()) : null;
            warehouse.setPicture(pic);
            warehouseRepository.save(warehouse);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private String buildUserDpUrl(Person person) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users")
                .path("/" + person.getUsername())
                .path("/dp")
                .toUriString();
    }
    private String buildItemPictureUrl(Item item) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/inventory")
                .path("/warehouses")
                .path("/" + item.getWarehouse().getName())
                .path("/items")
                .path("/" + item.getUuid())
                .path("/picture")
                .toUriString();
    }
    private String buildWarehousePictureUrl(Warehouse warehouse) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/mobile")
                .path("/inventory")
                .path("/warehouses")
                .path("/" + warehouse.getName())
                .path("/picture").toUriString();
    }

    //TODO Find a way to do a more proper conversion
    private Byte[] primToWrap(byte[] primBytes) {
        Byte[] wrapBytes = new Byte[primBytes.length];
        for (int i = 0; i < wrapBytes.length; i++) {
            wrapBytes[i] = primBytes[i];
        }
        return wrapBytes;
    }
    private byte[] wrapToPrim(Byte[] wrapBytes) {
        byte[] primBytes = new byte[wrapBytes.length];
        for (int i = 0; i < primBytes.length; i++) {
            primBytes[i] = wrapBytes[i];
        }
        return primBytes;
    }
}
