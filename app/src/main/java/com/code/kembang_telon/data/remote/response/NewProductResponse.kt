package com.code.kembang_telon.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewProductResponse(

	@field:SerializedName("data")
	val data: DataItem? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ProductsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("besar_diskon")
	val besarDiskon: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("discount_name")
	val discountName: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("category")
	val category: CategoryObject? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("discount_id")
	val discountId: Any? = null
)

data class CategoryObject(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("parent_id")
	val parentId: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class DataItem(

	@field:SerializedName("discounts")
	val discounts: List<DiscountsItem?>? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
)

data class DiscountsItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("besar_diskon")
	val besarDiskon: String? = null,

	@field:SerializedName("discount_name")
	val discountName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class CategoriesItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("parent_id")
	val parentId: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)
