package com.britel.api.model;

import javax.persistence.DiscriminatorValue;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@DiscriminatorValue(value="COMPANY")
public class Company extends User {

}
