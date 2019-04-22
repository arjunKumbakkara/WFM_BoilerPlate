package com.sixdee.wfm.common;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
 */
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

@Component
@Slf4j
public class WfmBeanUtils implements Constants {
	public static Logger log = LogManager.getLogger(WfmBeanUtils.class);

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	/**
	 * Helps to map a list of source to a list of destination
	 * @param fromList
	 * @param toClass
	 * @return
	 */
	public <F,T> List<T> mapList(List<F> fromList, final Class<T> toClass) {
		return fromList.stream()
				.map(from -> this.dozerBeanMapper.map(from, toClass))
				.collect(Collectors.toList());
	}

	/**
	 * Helps map a source to a destination
	 * @param from
	 * @param toClass
	 * @return
	 */
	public <T> T map(Object from, Class<T> toClass) {
		return this.dozerBeanMapper.map(from, toClass);
	}

	/**
	 * Helps get today's Date
	 * @return
	 */
	public static Date getTodayDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Helps in formatting Date to String
	 * @param date
	 * @return
	 */
	public static Date toDate(String date) {
		if (Strings.isEmpty(date)) return null;
		try {
			return formatter.parse(date);
		}
		catch (Exception e) {
			log.error("There is an exception when parsing {}", date, e);
			return null;
		}
	}

	/**
	 * Helps in formatting Date to String
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return displayFormatter.format(date);
	}

	/**
	 * Helps in getting the field name from the getter of a pojo.
	 * @param date
	 * @return
	 */
	public  String getFieldName(@SuppressWarnings("rawtypes") Class className)
	{
		try
		{
			BeanInfo info = Introspector.getBeanInfo(className);  
			PropertyDescriptor[] props = info.getPropertyDescriptors();  
			for (PropertyDescriptor pd : props) 
			{  
				if(pd.getName().contains("Id"))
				{
					return pd.getName();
				}
			}
		}
		catch (IntrospectionException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

}
