package app.utils;

import app.models.requests.AddItemRequest;
import app.models.requests.LoginRequest;
import app.models.requests.RegistrationRequest;
import app.models.requests.UpdateItemRequest;
import app.models.responses.ItemResponse;
import app.models.responses.PersonResponse;
import app.models.responses.WarehouseResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface MobileService {
    public ResponseEntity register(RegistrationRequest registrationRequest);
    public ResponseEntity login(LoginRequest loginRequest);

    List<WarehouseResponse> getWarehouses(Principal principal, boolean mine);

    WarehouseResponse getSpecificWarehouse(String warehouseName);

    List<ItemResponse> getAllItemsForUser(Principal principal, String warehouseName);

    ItemResponse addItemForUser(Principal principal, String warehouseName, AddItemRequest addItemRequest);

    ItemResponse getItemForUser(Principal principal, String warehouseName, String uuid);

    ItemResponse updateItemForUser(Principal principal, String warehouseName, String uuid, UpdateItemRequest updateItemRequest);

    ResponseEntity deleteItemForUser(Principal principal, String warehouseName, String uuid);

    List<PersonResponse> getAllUsersAdmin(Principal principal);

    PersonResponse getUserAdmin(Principal principal, String username);

    ResponseEntity<ByteArrayResource> getUserPicture(String username);

    ResponseEntity<ByteArrayResource> getItemPicture(String warehouseName, String uuid);

    ResponseEntity<ByteArrayResource> getWarehousePicture(String warehouseName);

    ResponseEntity updateUserPicture(String username, MultipartFile dp);

    ResponseEntity updateItemPicture(String warehouseName, String uuid, MultipartFile picture);

    ResponseEntity updateWarehousePicture(String warehouseName, MultipartFile picture);
}
