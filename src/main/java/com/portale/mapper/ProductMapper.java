package com.portale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.StorageObject;
import com.portale.model.StoreObject;

public interface ProductMapper {
	StoreObject GetStoreWithProducts(@Param("storeId") Long storeId);

	List<StorageObject> GetStoreProductsManager(@Param("storeId") Long storeId);

	// List<SubStorageObject> GetSubStorages(@Param("storeId")Long storeId);

	// ItemObject GetStoreProductinfo(@Param("store_id") Long store_id,
	// @Param("product_id") Long product_id);

	/*
	 * void UpdateSotreProductDetails(@Param("product_id") Long
	 * product_id, @Param("name") String name,
	 * 
	 * @Param("comment") String comment, @Param("category") String
	 * category, @Param("stato") Boolean stato,
	 * 
	 * @Param("prezzo") Long prezzo, @Param("spedizione") Boolean
	 * spedizione, @Param("quantita") Long quantita,
	 * 
	 * @Param("data_creazione") Date data_creazione);
	 */

	void DeleteStoreProductImages(@Param("images") String[] images);
}