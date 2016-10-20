package com.ricex.cartracker.web.configuration;

import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.gson.Gson;
import com.ricex.cartracker.data.manager.CarManager;
import com.ricex.cartracker.data.manager.ReaderLogManager;
import com.ricex.cartracker.data.manager.ReadingManager;
import com.ricex.cartracker.data.manager.TripManager;
import com.ricex.cartracker.data.mapper.CarMapper;
import com.ricex.cartracker.data.mapper.ReaderLogMapper;
import com.ricex.cartracker.data.mapper.ReadingMapper;
import com.ricex.cartracker.data.mapper.TripMapper;
import com.ricex.cartracker.data.validation.CarValidator;
import com.ricex.cartracker.data.validation.ReaderLogValidator;
import com.ricex.cartracker.data.validation.ReadingValidator;
import com.ricex.cartracker.data.validation.TripValidator;
import com.ricex.cartracker.web.controller.api.CarController;
import com.ricex.cartracker.web.controller.api.ReaderLogController;
import com.ricex.cartracker.web.controller.api.ReadingController;
import com.ricex.cartracker.web.controller.api.TripController;
import com.ricex.cartracker.web.controller.view.CarViewController;
import com.ricex.cartracker.web.controller.view.HomeController;
import com.ricex.cartracker.web.controller.view.TripViewController;
import com.ricex.cartracker.web.processor.TripProcessor;
import com.ricex.cartracker.web.util.GsonFactory;

@Configuration
public class ApplicationConfiguration extends WebMvcConfigurationSupport {

	
	/// ---- Define the Controllers ---- ///
	
	@Bean
	public CarController carController() throws Exception {
		return new CarController(carManager());
	}
	
	@Bean
	public TripController tripController() throws Exception {
		return new TripController(tripManager(), tripProcessor());
	}
	
	@Bean
	public ReaderLogController readerLogController() throws Exception {
		return new ReaderLogController(readerLogManager());
	}
	
	@Bean
	public ReadingController readingController() throws Exception {
		return new ReadingController(readingManager());
	}
	
	@Bean
	public CarViewController carViewController() {
		return new CarViewController();
	}
	
	@Bean
	public TripViewController tripViewController() {
		return new TripViewController();
	}
	
	@Bean
	public HomeController homeController() {
		return new HomeController();
	}
	
	@Bean
	public TripProcessor tripProcessor() throws Exception {
		return new TripProcessor(tripManager(), readingManager());
	}
	
	/// ---- Define the Managers ---- ///
	
	@Bean
	public CarManager carManager() throws Exception {
		return new CarManager(carMapper(), carValidator(), tripManager());
	}
	
	@Bean
	public TripManager tripManager() throws Exception {
		return new TripManager(tripMapper(), tripValidator(), carValidator());
	}
	
	@Bean
	public ReaderLogManager readerLogManager() throws Exception {
		return new ReaderLogManager(readerLogMapper(), readerLogValidator());
	}	
	
	@Bean
	public ReadingManager readingManager() throws Exception {
		return new ReadingManager(readingMapper(), readingValidator(), tripValidator());
	}
	
	/// ---- Define the Validators ---- ///
	
	public CarValidator carValidator() throws Exception { 
		return new CarValidator(carMapper());
	}
	
	public TripValidator tripValidator() throws Exception {
		return new TripValidator(tripMapper());
	}
	
	public ReaderLogValidator readerLogValidator() throws Exception {
		return new ReaderLogValidator(readerLogMapper());
	}
	
	public ReadingValidator readingValidator() throws Exception {
		return new ReadingValidator(readingMapper());
	}
	
	/// ---- Define the Mappers ---- ///
	
	@Bean
	public CarMapper carMapper() throws Exception { 
		MapperFactoryBean<CarMapper> mapperFactoryBean = new MapperFactoryBean<CarMapper>();
		mapperFactoryBean.setMapperInterface(CarMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	@Bean
	public TripMapper tripMapper() throws Exception {
		MapperFactoryBean<TripMapper> mapperFactoryBean = new MapperFactoryBean<TripMapper>();
		mapperFactoryBean.setMapperInterface(TripMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	
	@Bean
	public ReaderLogMapper readerLogMapper() throws Exception {
		MapperFactoryBean<ReaderLogMapper> mapperFactoryBean = new MapperFactoryBean<ReaderLogMapper>();
		mapperFactoryBean.setMapperInterface(ReaderLogMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	@Bean
	public ReadingMapper readingMapper() throws Exception {
		MapperFactoryBean<ReadingMapper> mapperFactoryBean = new MapperFactoryBean<ReadingMapper>();
		mapperFactoryBean.setMapperInterface(ReadingMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}
	
	
	@Bean(destroyMethod="")
	public DataSource dataSource() {
		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		return dsLookup.getDataSource("java:comp/env/jdbc/CarTrackerDatabase");
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		sqlSessionFactoryBean.setDataSource(dataSource());
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public String googleMapsApiKey() throws NamingException {
		JndiTemplate template = new JndiTemplate();
		return template.lookup("java:comp/env/CARTRACKER_GOOGLE_MAPS_API_KEY", String.class); 
	}
	
	@Bean
	public Gson gsonBean() {
		return gsonFactory().constructGson();
	}
	
	@Bean
	public GsonFactory gsonFactory() {
		return new GsonFactory();
	}	

	@Bean
	public GsonHttpMessageConverter gsonMessageConverter() {
		return new GsonHttpMessageConverter(gsonBean());
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(gsonMessageConverter());
		addDefaultHttpMessageConverters(converters);
	};
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
}
