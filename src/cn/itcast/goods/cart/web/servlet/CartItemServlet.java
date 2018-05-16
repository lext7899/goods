package cn.itcast.goods.cart.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.cart.domain.CartItem;
import cn.itcast.goods.cart.service.CartItemService;
import cn.itcast.goods.user.domain.User;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CartItemServlet
 */
public class CartItemServlet extends BaseServlet {
	private CartItemService cartItemService =new CartItemService();

	//我的购物车
	public String myCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user=(User) req.getSession().getAttribute("sessionUser");
		String uid =user.getUid();
		List<CartItem> cartItemList =cartItemService.myCart(uid);
		req.setAttribute("cartItemList", cartItemList);
		return "f:/jsps/cart/list.jsp";
	}
	
	
	//添加条目
	public  String add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Map map=req.getParameterMap();
	CartItem cartItem =CommonUtils.toBean(map, CartItem.class);
	Book book =CommonUtils.toBean(map, Book.class);
	User user=(User) req.getSession().getAttribute("sessionUser");
	cartItem.setBook(book);
	cartItem.setUser(user);
	cartItemService.add(cartItem);
	return myCart(req,resp);
	
	}
	//删除
	public  String batchDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String cartItemIds=req.getParameter("cartItemIds");
	cartItemService.batchDelect(cartItemIds);
	return myCart(req,resp);
	}
	
	public  String updateQuantity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cartItemId=req.getParameter("cartItemId");
		int quantity=Integer.parseInt(req.getParameter("quantity"));
		CartItem cartItem =cartItemService.updateQuantity(cartItemId, quantity);
		StringBuilder sb=new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");
		System.out.println(sb);
		resp.getWriter().println(sb);
		return null;

	}
	//加载多个cartItem
	public  String loadCartItems(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String cartItemIds=req.getParameter("cartItemIds");
		double total =Double.parseDouble(req.getParameter("total"));
		List<CartItem> cartItemList=cartItemService.loadCartItems(cartItemIds);
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("total", total);
		return "f:/jsps/cart/showitem.jsp";
 	
	
	}
	
}
