package net.aman.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import net.aman.shoppingbackend.dto.Product;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.aman.shoppingbackend.dao.CartLineDAO;
import net.aman.shoppingbackend.dao.ProductDAO;
import net.aman.shoppingbackend.dao.UserDAO;
import net.aman.shoppingbackend.dto.Cart;
import net.aman.shoppingbackend.dto.CartLine;
import net.aman.shoppingbackend.dto.User;

public class CartLineTestCase {

	

	private static AnnotationConfigApplicationContext context=null;
	
	
	private static CartLineDAO cartLineDAO;
	private static ProductDAO productDAO;
	private static UserDAO userDAO;
	
	
	private CartLine cartLine = null;
	
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		System.out.println("Hello");
		context.scan("net.aman.shoppingbackend");
		System.out.println("12 Hello");
		context.refresh();
		//context.refresh();
		System.out.println("13 Hello");
		System.out.println("Hello");
		cartLineDAO = (CartLineDAO)context.getBean("cartLineDAO");
		System.out.println("1 Hello");
		productDAO = (ProductDAO)context.getBean("productDAO");
		System.out.println("2 Hello");
		userDAO = (UserDAO)context.getBean("userDAO");
	}
	

/*	@Test
	public void testAddCartLine() {
		
		// fetch the user and then cart of that user
		User user = userDAO.getByEmail("absr@gmail.com");		
		Cart cart = user.getCart();
		
		// fetch the product 
		Product product = productDAO.get(2);
		
		// Create a new CartLine
		cartLine = new CartLine();
		cartLine.setCartId(cart.getId());
		cartLine.setProduct(product);
		cartLine.setProductCount(1);
		
		double oldTotal = cartLine.getTotal();		
		
		cartLine.setTotal(product.getUnitPrice() * cartLine.getProductCount());
		
		cart.setCartLines(cart.getCartLines() + 1);
		cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));
		
		assertEquals("Failed to add the CartLine!",true, cartLineDAO.add(cartLine));
		assertEquals("Failed to update the cart!",true, userDAO.updateCart(cart));
		
	}*/
	

	
	@Test
	public void testUpdateCartLine() {

		// fetch the user and then cart of that user
		User user = userDAO.getByEmail("absr@gmail.com");		
		Cart cart = user.getCart();
				
		cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), 2);
		
		cartLine.setProductCount(cartLine.getProductCount() + 1);
				
		double oldTotal = cartLine.getTotal();
				
		cartLine.setTotal(cartLine.getProduct().getUnitPrice() * cartLine.getProductCount());
		
		cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));
		
		assertEquals("Failed to update the CartLine!",true, cartLineDAO.update(cartLine));	

		
	}
	
	
	
}
