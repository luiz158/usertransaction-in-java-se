package br.eti.augusto.transactional;

import javax.annotation.Resource;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.jnp.interfaces.NamingParser;
import org.jnp.server.NamingBeanImpl;

import com.arjuna.ats.jta.common.jtaPropertyManager;
import com.arjuna.ats.jta.utils.JNDIManager;

public class UserTransactionProducer {

	private static final NamingBeanImpl NAMING_BEAN = new NamingBeanImpl();

	@Produces
	private TransactionManager tmProducer() throws Exception {
		//System.setProperty("ObjectStoreBaseDir", "target");

		// Start JNDI server
		NAMING_BEAN.start();

		// Bind JTA implementation with default names
		JNDIManager.bindJTAImplementation();

		// Bind JTA implementation with JBoss names. Needed for JTA 1.2
		// implementation.
		// See https://issues.jboss.org/browse/JBTM-2054
		NAMING_BEAN.getNamingInstance().createSubcontext(new NamingParser().parse("jboss"));
		jtaPropertyManager.getJTAEnvironmentBean()
				.setTransactionManagerJNDIContext("java:/jboss/TransactionManager");
		jtaPropertyManager.getJTAEnvironmentBean()
		        .setTransactionSynchronizationRegistryJNDIContext("java:/jboss/TransactionSynchronizationRegistry");
		JNDIManager.bindJTAImplementation();

		TransactionManager transactionManager = (TransactionManager) new InitialContext()
		        .lookup("java:/TransactionManager");
		return transactionManager;
	}
	
	private void close(@Disposes TransactionManager tm) {
		// Stop JNDI server
		NAMING_BEAN.stop();
	}
	
	//@Produces
	//@Resource
	//private UserTransaction xxx;
	
	@Produces
	public UserTransaction getUserTransaction(TransactionManager tm) {
		//tm.getTransaction();
		//com.arjuna.ats.arjuna.coordinator.
		//return new com.arjuna.ats.internal.jta.transaction.arjunacore.UserTransactionImple();
		UserTransaction utx = com.arjuna.ats.jta.UserTransaction.userTransaction();
		return utx;
	}

}
