package net.aman.shoppingbackend.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "net.aman.shoppingbackend.dto" })
@EnableTransactionManagement
@PropertySource("classpath:connection.properties")
public class HibernateConfig {

	@Value("${jdbc.url}")
	private String DATABASE_URL;
	@Value("${jdbc.driver}")
	private String DATABASE_DRIVER;
	@Value("${jdbc.dialect}")
	private String DATABASE_DIALECT;
	@Value("${jdbc.username}")
	private String DATABASE_USERNAME;
	@Value("${jdbc.password}")
	private String DATABASE_PASSWORD;

	// dataSource bean will be available
	@Bean("dataSource")
	public DataSource getDataSource() {

		BasicDataSource dataSource = new BasicDataSource();

		try {
			// Providing the database connection information
			dataSource.setDriverClassName(DATABASE_DRIVER);
			//dataSource.setDriverClassName("org.h2.Driver");
			// some modification in
			System.out.println("Session Connetion");

			// System.out.print(dataSource.getUrl());
			 dataSource.setUrl(DATABASE_URL);
			boolean b;
			//dataSource.setUrl("jdbc:h2:tcp://localhost/~/onlineshopping");
			System.out.println("2");
			dataSource.setUsername(DATABASE_USERNAME);
			//dataSource.setUsername("sa");
			System.out.println("3");
			 dataSource.setPassword(DATABASE_PASSWORD);
			//dataSource.setPassword("");
			// **********************************
			System.out.println("4");

		} catch (Exception ex) {
			System.out.println("Connection not Stable");

		}
		/*
		 * System.out.println(dataSource.getUsername());
		 * System.out.println(dataSource.getPassword());
		 * System.out.println(dataSource.getUrl());
		 */
		return dataSource;

	}

	// sessionFactory bean will be available

	@Bean
	public  SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder builder =null;
		try {
			builder = new LocalSessionFactoryBuilder(dataSource);
			builder.addProperties(getHibernateProperties());

			//builder.scanPackages("net.aman.shoppingbackend.dto");

			builder.scanPackages("net.aman.shoppingbackend.dto");
			//builder.scanPackages("")
	
		}catch(Exception ex) {
			System.out.println("Database in orm tool is not working in proper way");
		}
				// To check session SessionFactory work in proper os not
		org.hibernate.SessionFactory session = null;
		try {
			session = builder.buildSessionFactory();
			if(session==null) {
				System.out.println("Session is Not Connect It Never return ");
			}
			// HibernateUtil.getSessionFactory().openSession();
		} catch (HibernateException ex) {
		} finally {
			session.close();
		}

		return builder.buildSessionFactory();

	}

	// All the hibernate properties will be returned in this method
	private Properties getHibernateProperties() {

		Properties properties = new Properties();

		properties.put("hibernate.dialect", DATABASE_DIALECT);
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");

		// properties.put("hibernate.hbm2ddl.auto", "create");

		return properties;
	}

	// transactionManager bean
	@Bean
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

	/*@Bean public void getSessionFacictor() { org.hibernate.Session session =
	 null; try { System.out.println("Check seesion!");


	  session = HibernateTransactionManager.this.getSessionFactory().openSession();
	  session = HibernateUtil.getSessionFactory().openSession();

	  } catch (HibernateException ex) { } finally { session.close(); }
	  }*/


}
