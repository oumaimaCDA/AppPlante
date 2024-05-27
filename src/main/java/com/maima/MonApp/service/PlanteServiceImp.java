package com.maima.MonApp.service;


import com.maima.MonApp.dto.PlanteDto;
import com.maima.MonApp.model.Address;
import com.maima.MonApp.model.Plante;
import com.maima.MonApp.model.User;
import com.maima.MonApp.repository.AddresseRepository;
import com.maima.MonApp.repository.PlanteRepository;
import com.maima.MonApp.repository.UserRepository;
import com.maima.MonApp.request.CreatePlanteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlanteServiceImp implements PlanteService{

    @Autowired
    private PlanteRepository planteRepository;

    @Autowired
    private AddresseRepository addresseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Plante createPlante(CreatePlanteRequest req, User user) {

        Address address=addresseRepository.save(req.getAddress());

        Plante plante= new Plante();
        plante.setAddress(address);
        plante.setContactInformation(req.getContactInformation());
        plante.setPlanteType(req.getPlanteType());
        plante.setDescription(req.getDescription());
        plante.setImages(req.getImages());
        plante.setName(req.getName());
        plante.setOpeningHours(req.getOpningHours());
        plante.setRegistrationDate(LocalDateTime.now());
        plante.setCustomer(user);

        return planteRepository.save(plante);
    }


    @Override
    public Plante updatePlante(Long planteId, CreatePlanteRequest updatedPlante) throws Exception {

        Plante plante=findPlanteById(planteId);
        if (plante.getPlanteType()!=null){
           plante.setPlanteType(updatedPlante.getPlanteType());

         }
        if (plante.getDescription()!=null){
            plante.setDescription(updatedPlante.getDescription());
        }
        if (plante.getName()!=null){
            plante.setName(updatedPlante.getName());
        }

        return planteRepository.save(plante);
    }

    @Override
    public void deletePlante(Long planteId) throws Exception {
        Plante plante=findPlanteById(planteId);
        planteRepository.delete(plante);

    }

    @Override
    public List<Plante> getAllPlante() {
        return planteRepository.findAll();
    }

    @Override
    public List<Plante> searchPlante(String keyword) {
        return planteRepository.findBySearchQuery(keyword);
    }

    @Override
    public Plante findPlanteById(Long id) throws Exception {
        Optional<Plante> opt=planteRepository.findById(id);
        if (opt.isEmpty()){
            throw new Exception("plante not found with id"+id);
        }

        return opt.get();
    }

    @Override
    public Plante getPlanteByUserId(Long userId) throws Exception {
        Plante plante=planteRepository.findByCustomerId(userId);
        if(plante==null){
            throw new Exception("plante not found with customer id"+userId);
        }

        return plante;
    }

    @Override
    public PlanteDto addToFavorites(Long planteId, User user) throws Exception {
       Plante plante=findPlanteById(planteId);

       PlanteDto pl =new PlanteDto();
       pl.setDescription(plante.getDescription());
       pl.setImages(plante.getImages());
       pl.setTitle(plante.getName());
       pl.setId(planteId);

      if (user.getFavorites().contains(pl)){
          user.getFavorites().remove(pl);
      }
      else  user.getFavorites().add(pl);
      userRepository.save(user);
      return pl;
    }

    @Override
    public Plante updatePlanteStatus(Long id) throws Exception {
        Plante plante=findPlanteById(id);
        plante.setDisponible(!plante.isDisponible());

        return planteRepository.save(plante);
    }
}
