package com.ricex.cartracker.web.configuration;

import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ricex.cartracker.data.manager.auth.RegistrationKeyManager;
import com.ricex.cartracker.data.manager.auth.RegistrationKeyUseManager;
import com.ricex.cartracker.data.manager.auth.UserAuthenticationTokenManager;
import com.ricex.cartracker.data.manager.auth.UserManager;
import com.ricex.cartracker.data.mapper.CarMapper;
import com.ricex.cartracker.data.mapper.ReaderLogMapper;
import com.ricex.cartracker.data.mapper.ReadingMapper;
import com.ricex.cartracker.data.mapper.TripMapper;
import com.ricex.cartracker.data.mapper.auth.RegistrationKeyMapper;
import com.ricex.cartracker.data.mapper.auth.RegistrationKeyUseMapper;
import com.ricex.cartracker.data.mapper.auth.UserAuthenticationTokenMapper;
import com.ricex.cartracker.data.mapper.auth.UserMapper;
import com.ricex.cartracker.data.validation.CarValidator;
import com.ricex.cartracker.data.validation.ReaderLogValidator;
import com.ricex.cartracker.data.validation.ReadingValidator;
import com.ricex.cartracker.data.validation.TripValidator;
import com.ricex.cartracker.data.validation.auth.RegistrationKeyUseValidator;
import com.ricex.cartracker.data.validation.auth.RegistrationKeyValidator;
import com.ricex.cartracker.data.validation.auth.UserAuthenticationTokenValidator;
import com.ricex.cartracker.data.validation.auth.UserValidator;
import com.ricex.cartracker.web.auth.ProxyPasswordEncoder;
import com.ricex.cartracker.web.controller.api.CarController;
import com.ricex.cartracker.web.controller.api.ReaderLogController;
import com.ricex.cartracker.web.controller.api.ReadingController;
import com.ricex.cartracker.web.controller.api.RegistrationKeyController;
import com.ricex.cartracker.web.controller.api.TripController;
import com.ricex.cartracker.web.controller.api.UserAuthenticationTokenController;
import com.ricex.cartracker.web.controller.api.UserController;
import com.ricex.cartracker.web.controller.view.AdminViewController;
import com.ricex.cartracker.web.controller.view.CarViewController;
import com.ricex.cartracker.web.controller.view.HomeController;
import com.ricex.cartracker.web.controller.view.LogViewController;
import com.ricex.cartracker.web.controller.view.LoginViewController;
import com.ricex.cartracker.web.controller.view.TripViewController;
import com.ricex.cartracker.web.controller.view.UserViewController;
import com.ricex.cartracker.web.processor.TripProcessor;
import com.ricex.cartracker.web.util.GsonFactory;

@Configuration
public class ApplicationConfiguration extends WebMvcConfigurationSupport {

	@Autowired(required = true)
	public SecurityConfiguration securityConfiguration;
	
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
	public UserController userController() throws Exception {
		return new UserController(userManager(),
					userAuthenticationTokenManager(),
					securityConfiguration.apiUserAuthenticator());
	}
	
	@Bean
	public RegistrationKeyController registrationKeyController() throws Exception {
		return new RegistrationKeyController(registrationKeyManager());
	}
	
	@Bean
	public UserAuthenticationTokenController userAuthenticationTokenController() throws Exception {
		return new UserAuthenticationTokenController(userAuthenticationTokenManager());
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
	public AdminViewController adminViewController() {
		return new AdminViewController();
	}
	
	@Bean
	public UserViewController userViewController() {
		return new UserViewController();
	}
	
	@Bean
	public LogViewController logViewController() {
		return new LogViewController();
	}
	
	@Bean
	public TripProcessor tripProcessor() throws Exception {
		return new TripProcessor(tripManager(), readingManager(), carManager());
	}
	
	@Bean 
	public LoginViewController loginViewController() {
		return new LoginViewController();
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
	
	@Bean
	public UserManager userManager() throws Exception {
		return new UserManager(userMapper(), userValidator(), proxyPasswordEncoder(), registrationKeyManager());
	}
	
	@Bean
	public RegistrationKeyManager registrationKeyManager() throws Exception {
		return new RegistrationKeyManager(registrationKeyMapper(), registrationKeyValidator(), registrationKeyUseManager());
	}
	
	@Bean 
	public RegistrationKeyUseManager registrationKeyUseManager() throws Exception {
		return new RegistrationKeyUseManager(registrationKeyUseMapper(), registrationKeyUseValidator());
	}
	
	@Bean
	public UserAuthenticationTokenManager userAuthenticationTokenManager() throws Exception {
		return new UserAuthenticationTokenManager(userAuthenticationTokenMapper(),
				userAuthenticationTokenValidator(),
				userValidator());
				
	}
	
	
	/// ---- Define the Validators ---- ///
	@Bean
	public CarValidator carValidator() throws Exception { 
		return new CarValidator(carMapper());
	}
	
	@Bean
	public TripValidator tripValidator() throws Exception {
		return new TripValidator(tripMapper());
	}
	
	@Bean
	public ReaderLogValidator readerLogValidator() throws Exception {
		return new ReaderLogValidator(readerLogMapper());
	}
	
	@Bean
	public ReadingValidator readingValidator() throws Exception {
		return new ReadingValidator(readingMapper());
	}
	
	@Bean
	public UserValidator userValidator() throws Exception {
		return new UserValidator(userMapper());
	}
	
	@Bean
	public RegistrationKeyValidator registrationKeyValidator() throws Exception {
		return new RegistrationKeyValidator(registrationKeyMapper());
	}
	
	@Bean
	public RegistrationKeyUseValidator registrationKeyUseValidator() throws Exception {
		return new RegistrationKeyUseValidator(registrationKeyUseMapper());
	}
	
	@Bean
	public UserAuthenticationTokenValidator userAuthenticationTokenValidator() throws Exception {
		return new UserAuthenticationTokenValidator(userAuthenticationTokenMapper());
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
	
	@Bean
	public UserMapper userMapper() throws Exception {
		MapperFactoryBean<UserMapper> mapperFactoryBean = new MapperFactoryBean<UserMapper>();
		mapperFactoryBean.setMapperInterface(UserMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}	
	
	@Bean
	public RegistrationKeyMapper registrationKeyMapper() throws Exception {
		MapperFactoryBean<RegistrationKeyMapper> mapperFactoryBean = new MapperFactoryBean<RegistrationKeyMapper>();
		mapperFactoryBean.setMapperInterface(RegistrationKeyMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}	
	
	@Bean
	public RegistrationKeyUseMapper registrationKeyUseMapper() throws Exception {
		MapperFactoryBean<RegistrationKeyUseMapper> mapperFactoryBean = new MapperFactoryBean<RegistrationKeyUseMapper>();
		mapperFactoryBean.setMapperInterface(RegistrationKeyUseMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean.getObject();
	}	
	
	@Bean
	public UserAuthenticationTokenMapper userAuthenticationTokenMapper() throws Exception {
		MapperFactoryBean<UserAuthenticationTokenMapper> mapperFactoryBean = new MapperFactoryBean<UserAuthenticationTokenMapper>();
		mapperFactoryBean.setMapperInterface(UserAuthenticationTokenMapper.class);
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
	
	public ProxyPasswordEncoder proxyPasswordEncoder() {
		return new ProxyPasswordEncoder();
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
