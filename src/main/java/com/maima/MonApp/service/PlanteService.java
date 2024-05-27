package com.maima.MonApp.service;

import com.maima.MonApp.dto.PlanteDto;
import com.maima.MonApp.model.Plante;
import com.maima.MonApp.model.User;
import com.maima.MonApp.request.CreatePlanteRequest;

import java.util.List;



public interface PlanteService {

    public Plante createPlante(CreatePlanteRequest req, User user);


    public Plante updatePlante(Long planteId, CreatePlanteRequest updatedPlante) throws Exception;


    public void deletePlante(Long planteId) throws Exception;

    public List<Plante> getAllPlante();

    public List<Plante> searchPlante(String keyword);

    public Plante findPlanteById(Long id) throws  Exception;

    public Plante getPlanteByUserId(Long userId) throws Exception;

    public PlanteDto addToFavorites(Long planteId, User user) throws Exception;

    public Plante updatePlanteStatus(Long id )throws Exception;

}
