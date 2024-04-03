package com.brennanUOP.SDTP;

import com.brennanUOP.SDTP.Controller.SDTPController;
import com.brennanUOP.SDTP.Controller.SDTPGUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SDTPApplication {

	public static void main(String[] args) {
		//Start API
		SpringApplication.run(SDTPApplication.class, args);
		// Create an instance of SDTPController
		SDTPController apiController = new SDTPController();
		// Start GUI with the SDTPController instance
		javax.swing.SwingUtilities.invokeLater(() -> new SDTPGUI(apiController));
	}

}
