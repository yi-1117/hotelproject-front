package tw.com.ispan.service;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tw.com.ispan.domain.ProductBean;

@SpringBootTest
public class ProductServiceTests {
	@Autowired
	private ProductService productService;
	
	@Test
	public void testFind() {
		JSONObject obj = new JSONObject()
//				.put("name", "a")
				.put("start", 0)
				.put("rows", 3)
				.put("sort", "id")
				.put("dir", true);
		long count = productService.count(obj.toString());
		System.out.println("count="+count);
		
		List<ProductBean> find = productService.find(obj.toString());
		if(find!=null && !find.isEmpty()) {
			for(ProductBean bean : find) {
				System.out.println("bean="+bean);				
			}
		}
		System.out.println("----------");
	}
	
//	@Test
	public void testRemove() {
		boolean  remove1 = productService.exists(1000);
		System.out.println("remove1="+remove1);
		
		boolean  remove2 = productService.remove(200);
		System.out.println("remove2="+remove2);
		
		boolean  remove3 = productService.remove(null);
		System.out.println("remove3="+remove3);
		System.out.println("----------");
	}

//	@Test
	public void testUpdate() {
		JSONObject obj = new JSONObject()
				.put("id", 1000)
				.put("name", "hehehe")
				.put("price", 5.67)
				.put("make", "2024-11-30")
				.put("expire", 8);
		
		ProductBean modify = productService.modify(obj.toString());
		System.out.println("modify="+modify);
		System.out.println("----------");
	}
	
//	@Test
	public void testCreate() {
		JSONObject obj = new JSONObject()
				.put("id", 1000)
				.put("name", "hahaha")
				.put("price", 1.23)
				.put("make", "2024-11-29")
				.put("expire", 4);
		
		ProductBean create = productService.create(obj.toString());
		System.out.println("create="+create);
		System.out.println("----------");
	}
	
//	@Test
	public void testExists() {
		boolean  exists1 = productService.exists(2);
		System.out.println("exists1="+exists1);
		
		boolean  exists2 = productService.exists(200);
		System.out.println("exists2="+exists2);
		
		boolean  exists3 = productService.exists(null);
		System.out.println("exists3="+exists3);
		System.out.println("----------");
	}

//	@Test
	public void testFindById() {
		ProductBean findById1 = productService.findById(2);
		System.out.println("findById1="+findById1);
		
		ProductBean findById2 = productService.findById(200);
		System.out.println("findById2="+findById2);
		
		ProductBean findById3 = productService.findById(null);
		System.out.println("findById3="+findById3);
		System.out.println("----------");
	}
	
//	@Test
	public void testSelect() {
		List<ProductBean> selects1 = productService.select(null);
		System.err.println("products="+selects1);
		
		ProductBean bean = new ProductBean();
		bean.setId(1);
		List<ProductBean> selects2 = productService.select(bean);
		System.err.println("products="+selects2);
		System.out.println("----------");
	}
}
