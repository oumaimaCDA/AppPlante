//package com.maima.MonApp.model;
//
//import com.maima.MonApp.repository.PlanteRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class PlanteConfig {
//
//   @Bean
//    CommandLineRunner commandLineRunner(
//           PlanteRepository repository){
//        return args -> {
//            Plante persil = new Plante(
//                           2L,
//                    admin,
//                    "orchidée",
//                    "description d'une plante",
//                    "fleur",
//                "02 avenue dame",
//
//               "maima@gmail.com",
//            "Mon-Sun: 9:00 AM---9:00-PM",
//                    "order",
//                    "https://images.pexels.com/photos/1302305/pexels-photo-1302305.jpeg?auto=compress&cs=tinysrgb&w=600",
//                    "date",
//                    "disponible",
//                    "soins"
//
//
//
//
//            );
//            Plante romarin = new Plante(
//                    3L,
//                    "user",
//                    "orchidée",
//                    "description d'une plante",
//                    "fleur",
//                    "Addresse",
//
//                    "maima@gmail.com",
//                    "Mon-Sun: 9:00 AM---9:00-PM",
//                    "order",
//                    "https://images.pexels.com/photos/1302305/pexels-photo-1302305.jpeg?auto=compress&cs=tinysrgb&w=600",
//                    "01/02/2025",
//                    false,
//                    "soins"
//            );
//repository.saveAll(
//        List.of(persil,romarin)
//);
//      };
//    }
//}
