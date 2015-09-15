package org.labican.sabia.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hyago
 */
public class JPAUtil {
    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("SABIA_PU");
}
