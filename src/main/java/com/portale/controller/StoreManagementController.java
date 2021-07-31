package com.portale.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.HomeSettingsObj;
import com.portale.model.ItemObject;
import com.portale.model.NotificationObject;
import com.portale.model.PaginationObject;
import com.portale.model.PaginationSupportObject;
import com.portale.model.StorageObject;
import com.portale.model.StoreObject;
import com.portale.security.model.AuthenticatedUser;
import com.portale.services.ErrorHandlerService;
import com.portale.services.NotificationService;
import com.portale.services.StatisticsService;
import com.portale.services.StoreService;
import com.portale.services.ThemeService;
import com.portale.services.UserService;

@RestController
public class StoreManagementController {
	@Resource
	private StoreService storeService;
	@Resource
	private NotificationService notificationService;
	@Resource
	private UserService userService;
	@Resource
	private ThemeService themeService;
	@Resource
	private StatisticsService statisticsService;
	@Resource
	private ErrorHandlerService errorHandlerService;

	// ritorna la lista dei negozi

	@RequestMapping(value = "/store-management/stores", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoresListManager(
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "per_page", defaultValue = "10", required = false) int per_page,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "tp", defaultValue = "main", required = false) String tp, HttpServletRequest request,
			Authentication authentication) {

		PaginationObject obj = new PaginationObject();
		List<StoreObject> storesList = new ArrayList<StoreObject>();

		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		try {
			PaginationSupportObject pso = new PaginationSupportObject();
			page = page > 0 ? (page - 1) : 0;
			if (request.isUserInRole("ROLE_ADMIN")) {
				// obj.setTotalResult(2);
				storesList = storeService.GetStoreData((tp.equals("main") ? true : false), u.getUsr_id(), per_page,
						(page * per_page), search);
				pso.setTotalResults(storeService.GetStoreData_Count((tp.equals("main") ? true : false), u.getUsr_id(), search));
			} else {
				// obj.setTotalResult(storeService.GetStoresCount((int) u.getUsr_id(), search));
				storesList = storeService.GetStoreData(true, u.getUsr_id(), per_page, (page * per_page), search);
				pso.setTotalResults(storeService.GetStoreData_Count(true, u.getUsr_id(), search));
			}
			obj.setData(storesList);
			pso.setLastResultID(storesList.size() > 0 ? storesList.get(storesList.size()-1).getStore_id() : 0);
			obj.setPSO(pso);

			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ritorna informazione di un singolo negozio
	@RequestMapping(value = "/store-management/stores/{storeId}", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreInfoManager(@PathVariable("storeId") int storeId, HttpServletRequest request,
			Authentication authentication) {

		StoreObject storeDetails = new StoreObject();

		try {
			storeDetails = storeService.GetStoreInfo(storeId, request);
			if (storeDetails == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(storeDetails, HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ritorna la lista dei oggetti che si trovano in un storage
	@RequestMapping(value = "/store-management/stores/storage/{storageId}", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreStorageItemListManager(@PathVariable("storageId") int storageId,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "per_page", defaultValue = "10", required = false) int per_page,
			@RequestParam(value = "search", required = false) String search, HttpServletRequest request,
			Authentication authentication) {

		PaginationObject obj = new PaginationObject();
		StorageObject storageItemList = new StorageObject();

		try {
			page = page > 0 ? (page - 1) : 0;
			// obj.setTotalResult(storeService.GetStorageItemsCount(storageId, search));
			storageItemList = storeService.GetStorageItems(storageId, per_page, (page * per_page), search);
			obj.setData(storageItemList);

			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/*
	 * // la lista delle sottocategorie di un negozio
	 * 
	 * @RequestMapping(value = "/store-management/stores/{storeId}/substorages",
	 * method = RequestMethod.GET) public ResponseEntity<?>
	 * GetStoreSubStoragesManager(@PathVariable("storeId") Long storeId) {
	 * 
	 * List<StorageObject> subStorages = new ArrayList<StorageObject>();
	 * 
	 * try { subStorages = productService.GetSubStorages(storeId);
	 * 
	 * return new ResponseEntity<>(subStorages, HttpStatus.OK); } catch (Exception
	 * e) { System.out.println(e.getMessage()); return new
	 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	// la lista dei prodotti di un storage
	@RequestMapping(value = "/store-management/stores/{storeId}/storage", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreProductsManager(HttpServletRequest request, Authentication authentication,
			@PathVariable("storeId") Long storeId) {

		List<StorageObject> storageItems = new ArrayList<StorageObject>();

		try {
			storageItems = storeService.GetStoreProducts(storeId);

			return new ResponseEntity<>(storageItems, HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// informazione di un singolo prodotto di un negozio
	@RequestMapping(value = "/store-management/stores/products/{productId}", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreProductInfoManager(HttpServletRequest request, Authentication authentication,
			@PathVariable("productId") int productId) {

		ItemObject storeProductInfo = new ItemObject();

		try {
			storeProductInfo = storeService.GetStoreProductinfo(productId);
			return new ResponseEntity<>(storeProductInfo, HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/store-management/storage/{storageId}", method = RequestMethod.GET)
	public ResponseEntity<?> GetStorageInfoManager(HttpServletRequest request, Authentication authentication,
			@PathVariable("storageId") Long storageId) {

		try {
			StorageObject storageInfo = storeService.GetStorageInfo(storageId);
			return new ResponseEntity<>(storageInfo, HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// aggiornare i dati di un prodotto
	@RequestMapping(value = "/store-management/stores/storage/{storageId}/item/{itemId}", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> UpdateStoreProduct(HttpServletRequest request, Authentication authentication,
			@PathVariable("storageId") int storageId, @PathVariable("itemId") Long itemId,
			@RequestBody ItemObject _product) {
		try {

			storeService.SetItemImage(itemId, _product.getItem_media().get(0).getSelected_media_id());

			storeService.UpdateSotreProductDetails(itemId, _product.getItem_name(), _product.getItem_description(),
					_product.getItem_category(), _product.getUnit_price(), _product.getStoraged(),
					_product.getShipment_included(), _product.getQuality(), _product.getQuantity(),
					_product.getPubblication_date(), _product.getPreview_media());

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// aggiungere un nuovo negozio
	@RequestMapping(value = "/store-management/stores/add-store", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> AddStore(HttpServletRequest request, Authentication authentication,
			@RequestBody StoreObject _store) {
//,@RequestPart(value = "storeprice", required = false) Long _option

		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		try {

			storeService.AddNewStore(_store, _store.getStore_name(), _store.getStore_owner(), false,
					_store.getCategory(), new Date(), _store.getValid_until(), _store.getTheme().getThemeId(),
					request.isUserInRole("ROLE_ADMIN") ? true : false, _store.getStore_depth(),
					_store.getMedia().getSelected_media_id(), _store.getItems_type());

			// If administratore created the shop it will be automatically confirmed
			// If it's confirmed store must have a storage
			// By default if user request a store, no storage is added before store is
			// confirmed
			if (request.isUserInRole("ROLE_ADMIN") && _store.getStore_depth() == 0) {
				StorageObject storageObj = new StorageObject();
				storeService.AddStoreStorage(storageObj, _store.getStore_id(), _store.getItems_type() == 2 ? "Servizi" : "Prodotti", null,
						_store.getStore_depth() == 0 ? _store.getTheme().getThemeId() : new Long(1));
				CompletableFuture
						.runAsync(() -> statisticsService.CreateWSV_Prod(storageObj.getStorage_id().intValue(), 1));
			}

			NotificationObject notification = new NotificationObject();

			if (request.isUserInRole("ROLE_ADMIN")) {
				notification.setImportancyLevel(0);
				notification.setTitle("Un nuovo negozio è stato aggiunto.");
				notification.setMessage("L'utente " + u.getUsername() + " ha aggiunto un nuovo negozio. ("
						+ "<a href='?negozi=" + _store.getStore_id() + "&sname=" + _store.getStore_name() + "'>"
						+ _store.getStore_name() + "</a>" + ")");
				notification.setType(0);
			} else {
				notification.setImportancyLevel(1);
				notification.setTitle("Una nuova richiesta per aggiungere un nuovo negozio!");
				notification.setType(1);
				/*
				 * notification.setMessage("L'utente " + u.getUsername() +
				 * " ha inviato una richiesta per aggiungere il proprio negozio per un periodo "
				 * + (_option == 0 ? "di ( 1 Mese | 10 &euro; )." : (_option == 1 ?
				 * "di ( 3 Mesi | 30 &euro; )" : (_option == 2 ? "di ( 6 Mesi | 60 &euro; )" :
				 * (_option == 3 ? "di ( 1 Anno | 120 &euro; )" : "Indeterminato")))) +
				 * " Per visualizzare i dettagli del negozio e proseguire con la conferma cliccare questo"
				 * + " <a href='?negozi=" + _store.getStore_id() + "&sname=" +
				 * _store.getStore_name() + "'>link</a>.");
				 */
			}

			notificationService.CreateNotification(notification, notification.getTitle(), notification.getMessage(),
					notification.getImportancyLevel(), u.getUsr_id(), notification.getType());

			List<Integer> U = userService.GetUsersIdListByRole("ROLE_ADMIN");
			for (int a = 0; a < U.size(); a++) {
				int currentU = U.get(a);
				notificationService.AppendNotificationToUser(currentU, notification.getNotification_id());
			}
			CompletableFuture.runAsync(() -> statisticsService.CreateWSV_Prod(_store.getStore_id(), 0));
			return new ResponseEntity<>(_store, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			errorHandlerService.submitError(409, e, authentication, request);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// aggiungere un nuovo prodotto per un negozio
	@RequestMapping(value = "/store-management/stores/storage/{storageid}/add-item", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> AddStoreProduct(HttpServletRequest request, Authentication authentication,
			@PathVariable("storageid") Long storage_id, @RequestBody ItemObject ItemObject) {

		try {
			storeService.AddStorageItem(ItemObject, storage_id, ItemObject.getItem_name(),
					ItemObject.getItem_description(), ItemObject.getItem_category(), ItemObject.getUnit_price(),
					ItemObject.getStoraged(), ItemObject.getShipment_included(), ItemObject.getQuality(),
					ItemObject.getQuantity(), new Date(), ItemObject.getType());

			if (ItemObject.getItem_media().get(0).getSelected_media_id().size() > 0) {
				storeService.SetItemImage(ItemObject.getProduct_id(),
						ItemObject.getItem_media().get(0).getSelected_media_id());
			}

			storeService.UpdateSotreProductDetails(ItemObject.getProduct_id(), null, null, null, null, null, null, null,
					null, null, ItemObject.getPreview_media());

			CompletableFuture
					.runAsync(() -> statisticsService.CreateWSV_Prod(ItemObject.getProduct_id().intValue(), 2));
			return new ResponseEntity<>(ItemObject.getProduct_id(), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			errorHandlerService.submitError(409, e, authentication, request);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// aggiornare i dati di un negozio
	@RequestMapping(value = "/store-management/stores/{storeId}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> UpdateStore(HttpServletRequest request, Authentication authentication,
			@RequestBody StoreObject _store, @PathVariable("storeId") int storeId) {
		try {
			Long selectedMediaId = new Long(0);

			if (_store.getMedia() != null) {
				if (_store.getMedia().getSelected_media_id().size() == 0) {
					selectedMediaId = null;
				} else {
					selectedMediaId = _store.getMedia().getSelected_media_id().get(0);
				}
			}
			storeService.UpdateStore(storeId, _store.getStore_name(), _store.getStore_owner().intValue(),
					selectedMediaId, _store.getOpen(), _store.getCategory(), _store.getCreation(),
					_store.getValid_until(),
					_store.getTheme() != null ? _store.getTheme().getThemeId().intValue() : null,
					_store.getConfirmed());

			// UPDATE STORAGE THEME IF STORE DEPTH IS 0
			if (_store.getTheme() != null) {
				StoreObject _storeCheck = storeService.GetStoreInfo(storeId);
				if (_storeCheck.getStore_depth().equals(new Long(0)) && _storeCheck.getStorage().size() > 0) {
					storeService.UpdateStorage(_storeCheck.getStorage().get(0).getStorage_id(), null, null,
							_store.getTheme().getThemeId());
				}
			}

			return new ResponseEntity<>(storeId, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			errorHandlerService.submitError(409, e, authentication, request);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// aggiungere uno storage
	@RequestMapping(value = "/store-management/stores/{storeId}/storage/add-storage", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> AddStoreStorage(HttpServletRequest request, @RequestBody StorageObject _storage,
			@PathVariable("storeId") int storeId, Authentication authentication) {
		try {

			Long selectedMediaId = new Long(0);
			if (_storage.getStorage_media() != null) {
				if (_storage.getStorage_media().getSelected_media_id().size() == 0) {
					selectedMediaId = null;
				} else {
					selectedMediaId = _storage.getStorage_media().getSelected_media_id().get(0);
				}
			}

			storeService.AddStoreStorage(_storage, storeId, _storage.getStorage_name(),
					selectedMediaId, _storage.getTheme().getThemeId());
			CompletableFuture.runAsync(() -> statisticsService.CreateWSV_Prod(_storage.getStorage_id().intValue(), 1));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// aggiornare uno storage
	@RequestMapping(value = "/store-management/stores/{storeId}/storage/{storageId}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> UpdateStoreStorage(HttpServletRequest request, @RequestBody StorageObject _storage,
			@PathVariable("storeId") int storeId, @PathVariable("storageId") Long storageId,
			Authentication authentication) {
		try {

			Long selectedMediaId = new Long(0);
			if (_storage.getStorage_media() != null) {
				if (_storage.getStorage_media().getSelected_media_id().size() == 0) {
					selectedMediaId = null;
				} else {
					selectedMediaId = _storage.getStorage_media().getSelected_media_id().get(0);
				}
			}

			storeService.UpdateStorage(storageId, _storage.getStorage_name(),
					selectedMediaId, _storage.getTheme().getThemeId());

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/store-management/stores/updateorder", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> UpdateStoreOrder(HttpServletRequest request, @RequestBody HomeSettingsObj homeSettings,
			Authentication authentication) {

		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();

		try {
			if (u.getUsr_id() != 3) {
				try {
					Exception e = new Exception("User without rights tried to update home order.");
					errorHandlerService.submitError(403, e, authentication, request);
				} catch (Exception EXerrorHandlerService) {
					System.out.println(EXerrorHandlerService.getMessage());
				}
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			themeService.updateHomePage(homeSettings.getCol_num(), homeSettings.getIs_first_banner());
			themeService.homeSettingMem = homeSettings;

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "store-management/stores/{storeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> RemoveStore(HttpServletRequest request, Authentication authentication,
			@PathVariable("storeId") int storeId) {
		try {
			storeService.DeleteStore(storeId);
			CompletableFuture.runAsync(() -> statisticsService.DeleteWSV_Prod(storeId, 0));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/store-management/stores/storage/{storageId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> RemoveStorage(HttpServletRequest request, Authentication authentication,
			@PathVariable("storageId") int storageId) {
		try {
			storeService.DeleteStorage(storageId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/store-management/stores/products/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> RemoveItem(HttpServletRequest request, Authentication authentication,
			@PathVariable("itemId") int itemId) {
		try {
			storeService.DeleteItem(itemId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
