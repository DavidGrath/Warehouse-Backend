package app.utils;

import app.models.requests.AddItemRequest;
import app.models.requests.LoginRequest;
import app.models.requests.RegistrationRequest;
import app.models.requests.UpdateItemRequest;
import app.models.responses.ItemResponse;
import app.models.responses.PersonResponse;
import app.models.responses.WarehouseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/mobile")
public class MobileRestController {

    @Autowired
    MobileService mobileService;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return mobileService.login(loginRequest);
    }
    @PostMapping("/register")
    ResponseEntity<?> register(RegistrationRequest registrationRequest) {
        return mobileService.register(registrationRequest);
    }
    @GetMapping("/inventory/warehouses")
    List<WarehouseResponse> getWarehouses(Principal principal, @RequestParam(required = false, defaultValue = "onlyMine") String onlyMine) {
        boolean mine = onlyMine.equalsIgnoreCase("true");
        return mobileService.getWarehouses(principal, mine);
    }
    @GetMapping("/inventory/warehouses/{warehouse_name}")
    WarehouseResponse getSpecificWarehouse(@PathVariable("warehouse_name") String warehouseName) {
        return mobileService.getSpecificWarehouse(warehouseName);
    }
    @GetMapping("/inventory/warehouses/{warehouse_name}/items")
    List<ItemResponse> getAllItemsForUser(Principal principal, @PathVariable("warehouse_name") String warehouseName) {
        return mobileService.getAllItemsForUser(principal, warehouseName);
    }
    @PostMapping("/inventory/warehouses/{warehouse_name}/items")
    ItemResponse addItemForUser(Principal principal, @PathVariable("warehouse_name") String warehouseName, AddItemRequest addItemRequest) {
        return mobileService.addItemForUser(principal, warehouseName, addItemRequest);
    }
    @GetMapping("/inventory/warehouses/{warehouse_name}/items/{uuid}")
    ItemResponse getItemForUser(Principal principal, @PathVariable("warehouse_name") String warehouseName, @PathVariable("uuid") String uuid) {
        return mobileService.getItemForUser(principal, warehouseName, uuid);
    }
    @PutMapping("/inventory/warehouses/{warehouse_name}/items/{uuid}")
    ItemResponse updateItemForUser(Principal principal, @PathVariable("warehouse_name") String warehouseName, @PathVariable("uuid") String uuid, UpdateItemRequest updateItemRequest) {
        return mobileService.updateItemForUser(principal, warehouseName, uuid, updateItemRequest);
    }
    @DeleteMapping("/inventory/warehouses/{warehouse_name}/items/{uuid}")
    ResponseEntity deleteItemForUser(Principal principal, @PathVariable("warehouse_name") String warehouseName, @PathVariable("uuid") String uuid) {
        return mobileService.deleteItemForUser(principal, warehouseName, uuid);
    }
    @GetMapping("/admin/users")
    List<PersonResponse> getAllUsersAdmin(Principal principal) {
        return mobileService.getAllUsersAdmin(principal);
    }
    @GetMapping("/admin/users/{username}")
    PersonResponse getUserAdmin(Principal principal, @PathVariable String username) {
        return mobileService.getUserAdmin(principal, username);
    }


    @GetMapping("/users/{username}/dp")
    ResponseEntity<ByteArrayResource> getUserPicture(@PathVariable String username) {
        return mobileService.getUserPicture(username);
    }
    @GetMapping("/inventory/warehouses/{warehouse_name}/items/{uuid}/picture")
    ResponseEntity<ByteArrayResource> getItemPicture(@PathVariable("warehouse_name") String warehouseName, @PathVariable("uuid") String uuid) {
        return mobileService.getItemPicture(warehouseName, uuid);
    }
    @GetMapping("/inventory/warehouses/{warehouse_name}/picture")
    ResponseEntity<ByteArrayResource> getWarehousePicture(@PathVariable("warehouse_name") String warehouseName) {
        return mobileService.getWarehousePicture(warehouseName);
    }
    @PutMapping("/users/{username}/dp")
    ResponseEntity updateUserPicture(@PathVariable String username, MultipartFile dp) {
        return mobileService.updateUserPicture(username, dp);
    }
    @PutMapping("/inventory/warehouses/{warehouse_name}/items/{uuid}/picture")
    ResponseEntity updateItemPicture(@PathVariable("warehouse_name") String warehouseName, @PathVariable("uuid") String uuid, MultipartFile picture) {
        return mobileService.updateItemPicture(warehouseName, uuid, picture);
    }
    @PutMapping("/inventory/warehouses/{warehouse_name}/picture")
    ResponseEntity updateWarehousePicture(@PathVariable("warehouse_name") String warehouseName, MultipartFile picture) {
        return mobileService.updateWarehousePicture(warehouseName, picture);
    }
}
