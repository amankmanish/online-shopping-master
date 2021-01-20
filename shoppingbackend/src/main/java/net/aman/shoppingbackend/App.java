package net.aman.shoppingbackend;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import net.aman.shoppingbackend.config.HibernateConfig;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    		HibernateConfig cfg=new HibernateConfig();
        	DataSource dataSource=cfg.getDataSource();        	
        	SessionFactory session= new HibernateConfig().getSessionFactory(dataSource);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
}
