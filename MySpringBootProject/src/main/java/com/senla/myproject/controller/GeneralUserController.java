package com.senla.myproject.controller;

import com.senla.myproject.annotations.UserExceptionHandler;
import com.senla.myproject.dto.*;
import com.senla.myproject.model.*;
import com.senla.myproject.service.FreightExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController /* объединяет  @Controller и @ResponseBody => не только помечает класс как Spring MVC Controller,
//но и автоматически преобразует возвращаемые контроллером данные в формат JSON*/
//@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j // для логирования
@Tag(name="GeneralUserController") // для swagger-а
@UserExceptionHandler
public class GeneralUserController {

    private final FreightExchangeService service;

    private static final String LOGIN = "/login";
    private static final String MANAGER_PROFILE = "/orders";

    //ендпоинт
    @GetMapping("/")
    public String sayHello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        System.out.println("!!!!!!!!USER Security "+currentPrincipalName);
        return "Hello, " + currentPrincipalName;
    }

    /*@GetMapping (value="/")
    public String logUser(@ModelAttribute UserDto user, ModelMap model) {
        user = new UserDto();
        model.addAttribute("user", user);
        return LOGIN;
    }

    @PostMapping (value ="/")
    public String authorizeUser (@ModelAttribute("user") @Valid UserDto user, BindingResult result,
                                 ModelMap model, HttpSession httpSession){
        if (result.hasErrors()) {
            return LOGIN;
        }

        List <CarriageRequest> orders = service.findAllFreeOrders();

        List<CarrierManager> managerList = service.findAllCarrierManagers();
        for (CarrierManager manager: managerList){
            if (manager.getPassword().equals(user.getPassword())
                    & manager.getEmail().equals(user.getEmail())){
                model.addAttribute("orders",  orders);
                httpSession.setAttribute("user", user);
                return MANAGER_PROFILE;
            }
        }

        List <FreightForwarder> forwarderList = service.findAllForwarders();
        for (FreightForwarder forwarder: forwarderList){
            if (forwarder.getPassword().equals(user.getPassword())
                    & forwarder.getEmail().equals(user.getEmail())){
                model.addAttribute("orders",  orders);
                httpSession.setAttribute("user", user);
                return LOGIN;
            }
        }
        model.addAttribute("wrongdata", "Wrong username or password!");
        model.addAttribute("user", user);
        return LOGIN ;
    }*/

    //CarrierManager
    @GetMapping("/managers/{id}")
    public ResponseEntity  getCarrierManager (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the CarrierManager by id: "+id);
        CarrierManagerDto managerDto = service.findCarrierManagerById(id);
        return ObjectUtils.isEmpty(managerDto)
                    ? ResponseEntity.notFound().build() //ResponseEntity.ok(String.format("CarrierManager with id = %d not found", id))
                    : ResponseEntity.ok(managerDto);
    }

    @GetMapping("/managers/search_by_email")
    //http://localhost:8080/managers/search?email=kaiko%40gmail.com
    //@RequestParam извлекает значения из строки запроса,строка запроса начинается ?
    public ResponseEntity  getCarrierManagerWithEntityGraphByEmail (@RequestParam String email) {
        log.info("FROM UserController => Request to find the CarrierManager by email: "+email);
        CarrierManagerDto managerDto = service.findCarrierManagerWithEntityGraphByEmail(email);
        return ObjectUtils.isEmpty(managerDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(managerDto);
    }

    @GetMapping("/managers")
    public List<CarrierManager> getAllCarrierManagers(){
        log.info("FROM UserController => Request to find all CarrierManagers");
        return service.findAllCarrierManagers();
    }

    //Pagination
    @GetMapping("/pageable_managers")
    //http://localhost:8080/pageable_managers?page=0&size=1
    public Page<CarrierManagerDto> getAllNativeManagers(@RequestParam Integer page, @RequestParam Integer size){
        log.info("FROM UserController => Request to find all CarrierManagers with pagination");
        return service.findAllManagersNativeWithPagination(page,size);
    }

    //Order
    @GetMapping("/orders/{id}")
    public ResponseEntity  getCarriageRequest (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the CarriageRequest by id: "+id);
        CarriageRequestDto orderDto = service.findOrderById(id);
        return ObjectUtils.isEmpty(orderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(orderDto);
    }

    @GetMapping("/orders/search/{name}")
    //http://localhost:8080/orders/search/N-12345678
    //@PathVariable извлекает значения из пути URI
    public ResponseEntity  getCarriageRequestByNameIsLike (@PathVariable("name") String orderName) {
        log.info("FROM UserController => Request to find the CarriageRequest by name: "+orderName);
        CarriageRequestDto orderDto = service.findOrderByName(orderName);
        return ObjectUtils.isEmpty(orderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(orderDto);
    }

    @GetMapping("/orders")
    public List<CarriageRequest> getAllCarriageRequests(){
        log.info("FROM UserController => Request to find all CarriageRequests");
        return service.findAllOrders();
    }

    //Pagination
    @GetMapping("/pageable_orders")
    //http://localhost:8080/pageable_orders?page=0&size=5
    public Page<CarriageRequestDto> getAllNativeCarriageRequests(@RequestParam int page, @RequestParam int size){
        log.info("FROM UserController => Request to find all CarriageRequests with pagination");
        return service.findAllOrdersNativeWithPagination(page,size);
    }

    //Carrier
    @GetMapping("/carriers/{id}")
    public ResponseEntity  getCarriers (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the Carrier by id: "+id);
        CarrierDto carrierDto = service.findCarrierById(id);
        return ObjectUtils.isEmpty(carrierDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(carrierDto);
    }

    @GetMapping("/carriers")
    public List<Carrier> getAllCarriers(){
        log.info("FROM UserController => Request to find all Carriers");
        return service.findAllCarriers();
    }

    //Pagination
    @GetMapping("/pageable_carriers")
    //http://localhost:8080/pageable_carriers?page=0&size=1
    public Page<CarrierDto> getAllNativeCarriers(@RequestParam Integer page, @RequestParam Integer size){
        log.info("FROM UserController => Request to find all Carriers with pagination");
        return service.findAllCarriersNativeWithPagination(page,size);
    }

    // FreightForwarder
    @GetMapping("/forwarders/{id}")
    public ResponseEntity  getFreightForwarder (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the FreightForwarder by id: "+id);
        FreightForwarderDto forwarderDto = service.findFreightForwarderById(id);
        return ObjectUtils.isEmpty(forwarderDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(forwarderDto);
    }

    @GetMapping("/forwarders")
    public List<FreightForwarder> getAlFreightForwarders(){
        log.info("FROM UserController => Request to find all FreightForwarders");
        return service.findAllForwarders();
    }

    //Pagination
    @GetMapping("/pageable_forwarders")
    //http://localhost:8080/pageable_forwarders?page=0&size=1
    public Page<FreightForwarderDto> getAllNativeFreightForwarders(@RequestParam Integer page, @RequestParam Integer size){
        log.info("FROM UserController => Request to find all FreightForwarders with pagination");
        return service.findAllForwardersNativeWithPagination(page,size);
    }

    // TruckPark
    @GetMapping("/truck_parks/{id}")
    public ResponseEntity  getTruckPark (@PathVariable("id") Long id) {
        log.info("FROM UserController => Request to find the TruckPark by id: "+id);
        TruckParkDto parkDto = service.findTruckParkById(id);
        return ObjectUtils.isEmpty(parkDto)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(parkDto);
    }

    @GetMapping("/truck_parks")
    public List<TruckPark> getAllTruckParks(){
        log.info("FROM UserController => Request to find all TruckParks");
        return service.findAllTruckParks();
    }

    //Pagination
    @GetMapping("/pageable_truck_parks")
    //http://localhost:8080/pageable_truck_parks?page=0&size=1
    public Page<TruckParkDto> getAllNativeTruckParks(@RequestParam Integer page, @RequestParam Integer size){
        log.info("FROM UserController => Request to find all TruckParks with pagination");
        return service.findAllTruckParksNativeWithPagination(page,size);
    }
}
