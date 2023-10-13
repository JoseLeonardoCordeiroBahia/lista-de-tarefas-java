package br.com.joseleonardo.listadetarefas.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

	// Quando é static não precisa instanciar a classe
	public static void copyNonNullProperties(Object source, Object target) {
		// Atribui as propriedades e ignora as que foram nulas "mesclaando" as informações
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}

	// Pega todas as propriedades nulas
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper beanWrapper = new BeanWrapperImpl(source);

		PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();

		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			Object srcValue = beanWrapper.getPropertyValue(propertyDescriptor.getName());
			if (srcValue == null) {
				emptyNames.add(propertyDescriptor.getName());
			}
		}

		String[] result = new String[emptyNames.size()];

		return emptyNames.toArray(result);
	}

}