package cn.itcast.goods.category.service;

import java.sql.SQLException;
import java.util.List;

import javax.management.RuntimeErrorException;

import cn.itcast.goods.category.dao.CategoryDao;
import cn.itcast.goods.category.domain.Category;

/**
 * 分类模块业务层
 * @author qdmmy6
 *
 */
public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	
	/**
	 * 查询所有分类
	 * @return
	 */
	public List<Category> findAll() {
		try {
			return categoryDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
