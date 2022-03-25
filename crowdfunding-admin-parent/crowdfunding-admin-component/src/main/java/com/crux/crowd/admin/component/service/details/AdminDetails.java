package com.crux.crowd.admin.component.service.details;

import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.admin.component.service.impl.UserDetailsServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * admin细节类。为了能够拿到原始的admin，扩展{@link User}<br/>
 * @see UserDetailsServiceImpl#loadUserByUsername(String)
 */
public class AdminDetails extends User{

	private static final long serialVersionUID = 1L;
	private final Admin original;

	public AdminDetails(Admin original, Collection<? extends GrantedAuthority> authorities){
		super(original.getUserName(), original.getUserPswd(), authorities);
		this.original = original;
	}

	public Admin getOriginal(){
		return original;
	}
}
