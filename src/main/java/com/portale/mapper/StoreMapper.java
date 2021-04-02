package com.portale.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.ItemAds;
import com.portale.model.ItemObject;
import com.portale.model.StorageObject;
import com.portale.model.StoreObject;
import com.portale.model.ThemeObject;

public interface StoreMapper {
	Long GetStoresCount(@Param("store_owner") int store_owner, @Param("search") String search);

	Long GetStorageItemsCount(@Param("storageId") int storageId, @Param("search") String search);

	Long GetPublicStorageItemsCount(@Param("storageId") int storageId);

	StorageObject GetStorageItems(@Param("storageId") int storageId, @Param("limit") int limit,
			@Param("offset") int offset, @Param("search") String search);

	StorageObject GetStorageInfo(@Param("storageId") Long storageId);

	List<String> GetStoreCategoriesList();

	List<StoreObject> GetStoreData();

	List<StorageObject> GetStoreProductsManager(@Param("storeId") Long storeId);

	StoreObject GetStoreWithProducts(@Param("storeId") int storeId);

	StoreObject GetStorageeWithProducts(@Param("storageId") Long storageId, @Param("limit") int limit,
			@Param("offset") int offset);

	StoreObject GetStorageeWithAllProducts(@Param("storageId") Long storageId);

	ItemObject GetStoreProductinfo(@Param("product_id") int product_id);

	void UpdateSotreProductDetails(@Param("product_id") Long item_id, @Param("item_name") String item_name,
			@Param("item_description") String item_description, @Param("item_category") String item_category,
			@Param("unit_price") Long unit_price, @Param("storaged") Boolean storaged,
			@Param("shipment_included") Boolean shipment_included, @Param("quality") String quality,
			@Param("quantity") Long quantity, @Param("pubblication_date") Date pubblication_date,
			@Param("preview_media") Long preview_media);

	List<StoreObject> GetStoreDataManager(@Param("store_owner") int store_owner, @Param("limit") int limit,
			@Param("offset") int offset, @Param("search") String search);

	StoreObject GetStoreInfo(@Param("store_id") int store_id);

	StoreObject GetStoreInfo(@Param("store_id") int store_id, @Param("userName") String userName);

	List<ThemeObject> GetThemesData();

	void AddNewStore(@Param("store") StoreObject store, @Param("store_name") String store_name,
			@Param("store_owner") Long store_owner, @Param("open") Boolean open, @Param("category") String category,
			@Param("creation") Date creation, @Param("valid_until") Date valid_until, @Param("themeId") Long themeId,
			@Param("confirmed") Boolean confirmed, @Param("store_depth") Long store_depth,
			@Param("media_id") Long media_id);

	void UpdateStore(@Param("storeId") int storeId, @Param("store_name") String store_name,
			@Param("store_owner") int store_owner, @Param("media_id") Long media_id, @Param("open") Boolean open,
			@Param("category") String category, @Param("creation") Date creation,
			@Param("valid_until") Date valid_until, @Param("themeId") int themeId,
			@Param("confirmed") Boolean confirmed);

	void AddStoreStorage(@Param("storage") StorageObject storage, @Param("store_id") int store_id,
			@Param("storage_name") String storage_name, @Param("substorage_ref") Long substorage_ref,
			@Param("media_id") Long media_id, @Param("storage_theme") Long storage_theme);

	void UpdateStorage(@Param("storageId") Long storageId, @Param("storageName") String storageName,
			@Param("subStorage") Long subStorage, @Param("storage_media") Long storage_media,
			@Param("storage_theme") Long storage_themeId);

	void AddStorageItem(@Param("product") ItemObject product, @Param("storage_id") Long storage_id,
			@Param("item_name") String item_name, @Param("item_description") String item_description,
			@Param("item_category") String item_category, @Param("unit_price") Long unit_price,
			@Param("storaged") Boolean storaged, @Param("shipment_included") Boolean shipment_included,
			@Param("quality") String quality, @Param("quantity") Long quantity,
			@Param("pubblication_date") Date pubblication_date);

	Boolean isStoreConfirmed(@Param("storeId") int storeId);

	void DeleteStore(@Param("storeId") int storeId);

	void DeleteStorage(@Param("storageId") int storageId);

	void DeleteItem(@Param("itemId") int itemId);

	void SetItemImage(@Param("media_id") Long media_id, @Param("item_id") Long item_id);

	List<Long> GetItemImages(@Param("item_id") Long item_id);

	void RemoveItemImage(@Param("media_id") Long media_id, @Param("item_id") Long item_id);

	void SetPreferredItemImage(@Param("media_id") Long media_id, @Param("item_id") Long item_id);

	// Ritorna un prodotto casuale per far vedere nel tuttocitta
	ItemAds GetRandomItemTC();
}
