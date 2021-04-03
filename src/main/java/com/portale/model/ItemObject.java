package com.portale.model;

import java.util.Date;
import java.util.List;

public class ItemObject {
	
	private Long product_id;
	private Long storage_ref;
	private String storage_name;
	private String item_name;
	private String item_description;
	private String item_category;
	private Long unit_price;
	private Boolean storaged;
	private Boolean shipment_included;
	private String quality;
	private Long quantity;
	private Date pubblication_date;
	private Long preview_media;
	private List<MediaObject> item_media;
	private int type;
	
	private Long sub_store;
	
	
	
	public Long getStorage_ref() {
		return storage_ref;
	}
	public void setStorage_ref(Long storage_ref) {
		this.storage_ref = storage_ref;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	public String getItem_category() {
		return item_category;
	}
	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}
	public Long getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(Long unit_price) {
		this.unit_price = unit_price;
	}
	public Boolean getStoraged() {
		return storaged;
	}
	public void setStoraged(Boolean storaged) {
		this.storaged = storaged;
	}
	public Boolean getShipment_included() {
		return shipment_included;
	}
	public void setShipment_included(Boolean shipment_included) {
		this.shipment_included = shipment_included;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Date getPubblication_date() {
		return pubblication_date;
	}
	public void setPubblication_date(Date pubblication_date) {
		this.pubblication_date = pubblication_date;
	}
	public Long getPreview_media() {
		return preview_media;
	}
	public void setPreview_media(Long preview_media) {
		this.preview_media = preview_media;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	
	public Long getSub_store() {
		return sub_store;
	}
	public void setSub_store(Long sub_store) {
		this.sub_store = sub_store;
	}
	
	public List<MediaObject> getItem_media() {
		return item_media;
	}
	public void setItem_media(List<MediaObject> item_media) {
		this.item_media = item_media;
	}
	public String getStorage_name() {
		return storage_name;
	}
	public void setStorage_name(String storage_name) {
		this.storage_name = storage_name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
