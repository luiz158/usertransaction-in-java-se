package br.eti.augusto.transactional;

import java.io.IOException;

import org.jboss.weld.environment.se.Weld;

public class EntryPoint {

	public static void main(String[] args) throws IOException {
		Weld weld = new Weld();
		weld.initialize();
		weld.shutdown();
		System.exit(0);
	}

}
