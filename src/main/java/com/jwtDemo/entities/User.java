package com.jwtDemo.entities;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="usertab ")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String username;
	
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rolestab",
			joinColumns=@JoinColumn(name="id")
			)
	private Set<String> roles;
	
	
}
