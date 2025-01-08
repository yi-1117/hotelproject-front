package tw.com.ispan.repository;

import java.util.List;

import org.json.JSONObject;

import tw.com.ispan.domain.ProductBean;

public interface ProductDAO {
	public abstract Long count(JSONObject param);
	public abstract List<ProductBean> find(JSONObject param);
}