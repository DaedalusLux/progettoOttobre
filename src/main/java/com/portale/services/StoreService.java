package com.portale.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.StoreMapper;
import com.portale.model.ItemAds;
import com.portale.model.ItemObject;
import com.portale.model.StorageObject;
import com.portale.model.StoreObject;

@Service
@Repository
public class StoreService {
	@Autowired
	private StoreMapper mapper;
	// @Value("${local.dir.archive}")
	// private String archive;

	public Long GetStoresCount(int userId, String search) {
		if (search != null) {
			search = "%" + search + "%";
		}
		Long result = mapper.GetStoresCount(userId, search);
		return result == null ? 0 : result;
	}

	public Long GetStorageItemsCount(int storageId, String search) {
		if (search != null) {
			search = "%" + search + "%";
		}
		Long result = mapper.GetStorageItemsCount(storageId, search);
		return result == null ? 0 : result;
	}

	public Long GetPublicStorageItemsCount(int storageId) {
		Long result = mapper.GetPublicStorageItemsCount(storageId);
		return result == null ? 0 : result;
	}

	public StorageObject GetStorageItems(int storageId, int limit, int offset, String search) {
		return mapper.GetStorageItems(storageId, limit, offset, search);
	}

	public StorageObject GetStorageInfo(Long storageId) {
		return mapper.GetStorageInfo(storageId);
	}

	public List<StorageObject> GetStoreProducts(Long store_id) {
		return mapper.GetStoreProductsManager(store_id);
	}

	public StoreObject GetStoreWithProducts(int store_id) {
		return mapper.GetStoreWithProducts(store_id);
	}

	public StoreObject GetStorageeWithProducts(Long storageId, int limit, int offset) {
		return mapper.GetStorageeWithProducts(storageId, limit, offset);
	}

	public StoreObject GetStorageeWithAllProducts(Long storageId) {
		return mapper.GetStorageeWithAllProducts(storageId);
	}

	public ItemObject GetStoreProductinfo(int product_id) {
		ItemObject product_info = new ItemObject();
		product_info = mapper.GetStoreProductinfo(product_id);
		return product_info;
	}

	public void UpdateSotreProductDetails(Long item_id, String item_name, String item_description,
			String item_category, Long unit_price, Boolean storaged, Boolean shipment_included, String quality,
			Long quantity, Date pubblication_date, Long preview_media) {

		mapper.UpdateSotreProductDetails(item_id, item_name, item_description, item_category, unit_price, storaged,
				shipment_included, quality, quantity, pubblication_date, preview_media);
	}

	public List<String> GetStoreCategories(int dett) {
		List<String> lstCategories = new ArrayList<String>();
		switch (dett) {
		case 0:
			lstCategories = (List<String>) mapper.GetStoreCategoriesList();
			break;
		case 1:
			lstCategories = (List<String>) mapper.GetCategoryCategoriesList();
			break;
		case 2:
			lstCategories = (List<String>) mapper.GetProductCategoriesList();
			break;
		}
		return lstCategories;
	}

	public List<StoreObject> GetStoreData() {
		List<StoreObject> storesList = new ArrayList<StoreObject>();
		storesList = (List<StoreObject>) mapper.GetStoreData();
		return storesList;
	}

	public List<StoreObject> GetStoreData(boolean selfquery, int userId, int limit, int offset, String search) {
		return mapper.GetStoreDataManager(selfquery, userId, limit, offset, search);
	}

	public StoreObject GetStoreInfo(int store_id, HttpServletRequest request) {
		StoreObject storeInfo = new StoreObject();

		storeInfo = request.isUserInRole("ROLE_ADMIN") ? mapper.GetStoreInfo(store_id)
				: mapper.GetStoreInfo(store_id, request.getUserPrincipal().getName());
		return storeInfo;
	}

	public StoreObject GetStoreInfo(int store_id) {
		return mapper.GetStoreInfo(store_id);
	}

	public void AddNewStore(StoreObject store, String store_name, Long store_owner, Boolean open, String category,
			Date creation, Date valid_until, Long themeId, Boolean confirmed, Long store_depth,
			List<Long> selected_media_id, int items_type) {
		Long media_id = null;
		if (selected_media_id.size() > 0) {
			media_id = selected_media_id.get(0);
		}

		mapper.AddNewStore(store, store_name, store_owner, open, category, creation, valid_until, themeId, confirmed,
				store_depth, media_id, items_type);
	}

	public void UpdateStore(int storeId, String store_name, int store_owner, Long media_id, Boolean status,
			String category, Date creation, Date valid_until, int themeId, Boolean confirmed) {
		mapper.UpdateStore(storeId, store_name, store_owner, media_id, status, category, creation, valid_until, themeId,
				confirmed);
	}

	public void AddStoreStorage(StorageObject storageObject, int storeId, String storageName, Long storage_media, Long themeId) {

		mapper.AddStoreStorage(storageObject, storeId, storageName, storage_media, themeId);
	}

	public void UpdateStorage(Long storageId, String storageName, Long storage_media, Long themeId) {
		mapper.UpdateStorage(storageId, storageName, storage_media, themeId);
	}

	public void AddStorageItem(ItemObject product, Long storage_id, String item_name, String item_description,
			String item_category, Long unit_price, Boolean storaged, Boolean shipment_included, String quality,
			Long quantity, Date pubblication_date, int type) {

		mapper.AddStorageItem(product, storage_id, item_name, item_description, item_category, unit_price, storaged,
				shipment_included, quality, quantity, pubblication_date, type);
	}

	public void SetItemImage(Long item_id, List<Long> selected_media_id) {

		List<Long> itemImagesId = mapper.GetItemImages(item_id);

		for (int x = 0; x < itemImagesId.size(); x++) {
			if (selected_media_id.contains(itemImagesId.get(x))) {
				selected_media_id.remove(selected_media_id.indexOf(itemImagesId.get(x)));
			} else {
				mapper.RemoveItemImage(itemImagesId.get(x), item_id);
			}
		}

		for (int x = 0; x < selected_media_id.size(); x++) {
			mapper.SetItemImage(selected_media_id.get(x), item_id);
		}
	}

	public Boolean isStoreConfirmed(int storeId) {
		Boolean confirmed = mapper.isStoreConfirmed(storeId);
		if (confirmed != null) {
			if (confirmed == true) {
				return true;
			}
		}
		return false;
	}

	public void DeleteStore(int storeId) {
		mapper.DeleteStore(storeId);
	}

	public void DeleteStorage(int storageId) {
		mapper.DeleteStorage(storageId);
	}

	public void DeleteItem(int itemId) {
		mapper.DeleteItem(itemId);
	}

	public void SetItemImage(List<Long> media_id, Long productId) {
		for (int i = 0; i < media_id.size(); i++) {
			mapper.SetItemImage(media_id.get(i), productId);
		}
	}

	public void RemoveItemImage(List<Long> media_id, Long productId) {
		for (int i = 0; i < media_id.size(); i++) {
			mapper.RemoveItemImage(media_id.get(i), productId);
		}
	}

	public void SetPreferredItemImage(Long link_id, Long productId) {
		mapper.SetPreferredItemImage(link_id, productId);
	}

	/*
	 * public void DeleteItemImages(String[] images_path) { for (int i = 0; i <
	 * images_path.length; i++) { mapper.DeleteItemImages(images_path[i]); File file
	 * = new File(images_path[i]); if (file.delete()) { System.out.println("File " +
	 * images_path[i] + " deleted"); } else System.out.println("File " +
	 * images_path[i] + " doesn't exist"); } }
	 */

	// Ritorna un prodotto casuale per far vedere nel tuttocitta
	public ItemAds GetRandomItemTC() {
		return mapper.GetRandomItemTC();
	}
}
