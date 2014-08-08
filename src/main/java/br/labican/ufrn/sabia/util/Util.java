package br.labican.ufrn.sabia.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rummenigge Maia
 */
public class Util {

    public static final EntityManagerFactory EMF
            = Persistence.createEntityManagerFactory("SABIA_PU");
}
