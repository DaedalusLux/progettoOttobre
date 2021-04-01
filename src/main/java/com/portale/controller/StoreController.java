package com.portale.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portale.model.ItemAds;
import com.portale.model.ItemObject;
import com.portale.model.PaginationObject;
import com.portale.model.StoreObject;
import com.portale.services.StatisticsService;
import com.portale.services.StoreService;
import com.portale.services.ThemeService;

@RestController
public class StoreController {
	@Resource
	private StoreService storeService;
	@Resource
	private StatisticsService statisticsService;
	@Resource
	private ThemeService themeService;


	// lista dei negozi
	@RequestMapping(value = "/stores", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoresList(HttpServletRequest request,
			@RequestParam(value = "cA", defaultValue = "na") String cookieAccepted) {
		List<StoreObject> Store = new ArrayList<StoreObject>();
		try {

			if (themeService.homeSettingMem == null) {
				themeService.homeSettingMem = themeService.getHomeSettingsObjs();
				}

			Store = storeService.GetStoreData();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			String json = mapper.writeValueAsString(Store);
			json = "{ \"hs\": {\"hid\": \"" + themeService.homeSettingMem.getId_homepage() + "\", \"cn\":\""
					+ themeService.homeSettingMem.getCol_num() + "\",\"ifb\":\""
					+ themeService.homeSettingMem.getIs_first_banner().toString() + "\"}, \"stores\": " + json + "}";
			if (cookieAccepted.equals("ac")) {
				CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
				    //long running process
					statisticsService.AddWSV_Prod(0,0);
				});
				// CompletableFuture.runAsync(() -> {
				
				// });
			}

			return new ResponseEntity<>(json, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// informazioni di un negozio
	@RequestMapping(value = "/stores/{storeId}", method = RequestMethod.GET)
	public ResponseEntity<?> GetStore(@PathVariable("storeId") Long storeId) {

		StoreObject storeObj = new StoreObject();
		try {
			storeObj = storeService.GetStoreInfo(storeId);
			return new ResponseEntity<>(storeObj, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// lista dei prodotti di un storage
	@RequestMapping(value = "/stores/{storeId}/storage/{storageId}/products", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreProductsList(@PathVariable("storeId") Long storeId,
			@PathVariable("storageId") int storageId,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "per_page", defaultValue = "20", required = false) int per_page) {

		PaginationObject obj = new PaginationObject();
		StoreObject store = new StoreObject();
		try {
			page = page > 0 ? (page - 1) : 0;
			obj.setTotalResult(storeService.GetPublicStorageItemsCount(storageId));
			store = storeService.GetStorageeWithProducts(new Long(storageId), per_page, (page * per_page));
			obj.setData(store);
			// ObjectMapper mapper = new ObjectMapper();
			// mapper.setSerializationInclusion(Include.NON_NULL);
			// String json = mapper.writeValueAsString(store);

			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// lista di tutti i prodotti di uno storage con tutte le immagini
	@RequestMapping(value = "/stores/{storeId}/storage/{storageId}/allproducts", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreProductsList(@PathVariable("storeId") Long storeId,
			@PathVariable("storageId") int storageId) {

		StoreObject store = new StoreObject();
		try {
			store = storeService.GetStorageeWithAllProducts(new Long(storageId));

			return new ResponseEntity<>(store, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// lista dei prodotti di un negozio
	@RequestMapping(value = "/stores/{storeId}/products", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreProductsList(@PathVariable("storeId") Long storeId) {

		try {

			StoreObject store = storeService.GetStoreWithProducts(storeId);
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			String json = mapper.writeValueAsString(store);

			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// un prodotto di un negozio
	@RequestMapping(value = "/stores/products/{productId}", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreProductInfo(@PathVariable("productId") Long productId) {

		ItemObject productInformation = new ItemObject();
		try {
			productInformation = storeService.GetStoreProductinfo(productId);
			if (productInformation == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(productInformation, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/stores/categories", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreCategories() {
		try {
			List<String> storeCategories = storeService.GetStoreCategories();
			return new ResponseEntity<>(storeCategories, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Ritorna un prodotto casuale per far vedere nel tuttocitta
	@RequestMapping(value = "/stores/tuttocittaads", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoresList() {
		ItemAds itemAd = new ItemAds();
		try {
			itemAd = storeService.GetRandomItemTC();
			return new ResponseEntity<>(itemAd, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Ritorna la lista delle immagini che si trovano nella directory delle immagini
	// statiche var/www/html/Img/
	@RequestMapping(value = "/stores/staticontent", method = RequestMethod.GET)
	public ResponseEntity<?> GetStaticImagesList() {
		try {
			String[] pathnames;
			File f = new File("//var//www//html//Img");

			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File f, String name) {
					return name.contains(".");
				}
			};
			pathnames = f.list(filter);

			return new ResponseEntity<>(pathnames, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
