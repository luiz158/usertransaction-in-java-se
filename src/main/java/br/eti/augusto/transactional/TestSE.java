package br.eti.augusto.transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.InvalidTransactionException;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionRequiredException;
//import javax.transaction.UserTransaction;

import org.jboss.weld.environment.se.events.ContainerInitialized;

public class TestSE {
	// @Inject //since this was throwing an exception, I used @Resource
	//@Resource
	//@Inject
	//UserTransaction userTransaction;
	@Inject
	TransactionManager transactionManager;
	
	@Inject
	BeanMandatory beanMandatory;

	@Inject
	BeanNever beanNever;

	@Inject
	BeanNotSupported beanNotSupported;

	@Inject
	BeanRequired beanRequired;

	@Inject
	BeanRequiresNew beanRequiresNew;

	@Inject
	BeanSupports beanSupports;

	public void process(@Observes ContainerInitialized init) {
		String command = null;
		BufferedReader br = null;
		boolean loop = true;

		try {
			while (loop) {
				System.out.println("-----------------------");
				System.out.println("-----------------------");
				System.out.println("-----------------------");
				System.out.println("> 1 - Mandatory, 2 - Never, 3 - Not Supported, 4 - Required, 5 - Requires New, 6 - Supports, q - Quit");
				System.out.println("Enter your choice and hit enter:");
				br = new BufferedReader(new InputStreamReader(System.in));
				command = br.readLine();
				System.out.println("Received command[" + command + "]");

				switch (command) {
				case "1":
					System.out.println("Processing Mandatory");

					try {
						System.out.println("Scenario: Invoking outside transaction. Should get an error.");
						System.out.println(beanMandatory.getId());
						System.out.println("If you see this, it means there is something wrong!");
					} catch (Exception transactionalException) {
						if (transactionalException.getCause() instanceof TransactionRequiredException) {
							System.out.println("Got TransactionRequiredException for transactionalException.getCause() as expected.");
						} else {
							System.out.println("2 If you see this, it means there is something wrong!");
							System.out.println(transactionalException.getMessage());
						}
					}

					try {
						//userTransaction.begin();
						transactionManager.begin();
						System.out.println("Scenario: Invoking within a transaction.");
						System.out.println(beanMandatory.getId());
						//userTransaction.commit();
						transactionManager.commit();
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}

					break;
				case "2":
					System.out.println("Processing Never");

					try {
						System.out.println("Scenario: Invoking outside transaction.");
						System.out.println(beanNever.getId());
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}

					try {
						//userTransaction.begin();
						transactionManager.begin();
						System.out.println("Scenario: Invoking within a transaction. Should get an error.");
						System.out.println(beanNever.getId());
						System.out.println("If you see this, it means there is something wrong!");
					} catch (Exception transactionalException) {
						if (transactionalException.getCause() instanceof InvalidTransactionException) {
							System.out.println("Got InvalidTransactionException for transactionalException.getCause() as expected.");
						} else {
							System.out.println("If you see this, it means there is something wrong!");
							System.out.println(transactionalException.getMessage());
						}
					} finally {
						try {
							//userTransaction.rollback();
							transactionManager.rollback();
						} catch (Exception e) {
							System.out.println("Got unexpected exception in finally rollback for NEVER" + e.getMessage());
						}
					}

					break;
				case "3":
					System.out.println("Processing NotSupported");

					try {
						System.out.println("Scenario: Invoking outside transaction.");
						System.out.println(beanNotSupported.getId());
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}

					try {
						//userTransaction.begin();
						transactionManager.begin();
						System.out.println("Scenario: Invoking within a transaction. Transaction is suspended during the method call. ");
						System.out.println(beanNotSupported.getId());
						//userTransaction.commit();
						transactionManager.commit();
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}

					break;
				case "4":
					System.out.println("Processing Required");

					try {
						System.out.println("Scenario: Invoking outside transaction. Transaction would be started automatically for the method call.");
						System.out.println(beanRequired.getId());
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}

					try {
						//userTransaction.begin();
						transactionManager.begin();
						System.out.println("Scenario: Invoking within a transaction.");
						System.out.println(beanRequired.getId());
						//userTransaction.commit();
						transactionManager.commit();
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}

					break;
				case "5":
					System.out.println("Processing RequiresNew");

					try {
						System.out.println("Scenario: Invoking outside transaction. Transaction would be started automatically for the method call.");
						System.out.println(beanRequiresNew.getId());
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}

					try {
						//userTransaction.begin();
						transactionManager.begin();
						System.out.println("Scenario: Invoking within a transaction. NEW Transaction would be started automatically for the method call. ");
						System.out.println(beanRequiresNew.getId());
						//userTransaction.commit();
						transactionManager.commit();
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}

					break;
				case "6":
					System.out.println("Processing Supports");

					try {
						System.out.println("Scenario: Invoking outside transaction. Method is executed outside transaction. ");
						System.out.println(beanSupports.getId());
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}

					try {
						//userTransaction.begin();
						transactionManager.begin();
						System.out.println("Scenario: Invoking within a transaction. Method is executed within transaction context.");
						System.out.println(beanSupports.getId());
						//userTransaction.commit();
						transactionManager.commit();
					} catch (Exception e) {
						System.out.println("If you see this, it means there is something wrong!");
						System.out.println(e.getMessage());
					}
					
					break;
				case "q":
					System.out.println("Quitting...");
					loop = false;
				}// eof switch
			}// eof for
		} catch (Exception excp) {
			System.out.println("An exception has occured.");
			excp.printStackTrace();
		}
	}// eof process

}// eof class

