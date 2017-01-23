package bbs.listener;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import bbs.filter.AuthorityFilter;
import bbs.filter.EncodingFilter;
import bbs.filter.LoginFilter;

public class FilterRegister implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		ServletContext context = servletContextEvent.getServletContext();
		addFilter(context, "LoginFilter", LoginFilter.class, "/*");
		addFilter(context, "AuthorityFilter", AuthorityFilter.class, "/*");
		addFilter(context, "EncodingFilter", EncodingFilter.class, "/*");

	}

	private void addFilter(ServletContext context, String filterName,
			Class<? extends Filter> filterClass, String... urlPatterns) {

		context.addFilter(filterName, filterClass);

		FilterRegistration filterRegistration = context
				.getFilterRegistration(filterName);

		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(
				DispatcherType.REQUEST, DispatcherType.FORWARD);
		filterRegistration.addMappingForUrlPatterns(dispatcherTypes, false, urlPatterns);
	}
}
