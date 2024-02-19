package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Belong;
import com.example.demo.model.Nota;
import com.example.demo.model.Publication;
import com.example.demo.model.Usuario;
import com.example.demo.model.WorkSpace;
import com.example.demo.repository.NotaRepository;
import com.example.demo.repository.PublicationRepository;
import com.example.demo.repository.WorkSpaceRepository;
import com.example.demo.security.JWTAutenticator;

import jakarta.servlet.http.HttpServletRequest;



@CrossOrigin
@RestController
@RequestMapping("/publication/nota")
public class NotaController {

    @Autowired
    private NotaRepository nRep;

    @Autowired
    private PublicationRepository pRep;
    
    @Autowired
    private WorkSpaceRepository wkRep;
    
    
	@GetMapping(path = "/findByWorkSpaceId/{wsid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DTO> findAllNotasByWsid(@PathVariable  int wsid) {
    	WorkSpace wk = wkRep.findById(wsid);
        List<Publication> publications = pRep.findByWorkSpaceOrderByIdDesc(wk);
        List<Nota> notas = new ArrayList<>();
        List<DTO> obtainedNotas = new ArrayList<>();
        
        for (Publication publication : publications) {
            if(nRep.findByPid(publication.getId()) != null) {
                notas.add(nRep.findByPid(publication.getId()));
            }
        }
        
        for (Nota n : notas) {
            DTO obtainedNota = new DTO();

			obtainedNota.put("pId", n.getPid());
			obtainedNota.put("title", n.getTitle());
			obtainedNota.put("subtitle", n.getSubtitle());
			obtainedNota.put("content", n.getContent());
			
			obtainedNotas.add(obtainedNota);
		}
        
        return obtainedNotas;
    }
	
	/**
	 * Creates a new nota
	 * @param wkDTO
	 * @param request
	 * @return
	 */
	@PostMapping(path = "/insertNota", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO insertNota(@RequestBody DTO nDTO, HttpServletRequest request) {
		DTO result = new DTO();
		Publication p = new Publication();
		WorkSpace wk = wkRep.findById(Integer.parseInt(nDTO.get("wkid").toString()));
		Publication savedPublication = new Publication();

		try {
			
			if(wk != null) {
				p.setWorkSpace(wk);
				savedPublication = pRep.save(p);
				
				if(savedPublication.getId() > 0) {
					Nota nota = new Nota();
                    nota.setPid(savedPublication.getId());
                    nota.setTitle(nDTO.get("title").toString());
                    nota.setSubtitle(nDTO.get("subtitle").toString());
                    nota.setContent(nDTO.get("content").toString());
                    
                    nRep.save(nota);
                    result.put("result", "success");
				}
				else {
					result.put("result", "No se ha podido crear Nota");
				}
			}
			else {
				result.put("result", "No se ha encontrado espacio de trabajo");
			}

		} catch (Exception e) {
			result.put("result", "No ha sido posible crear Nota: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
    
    
    /**
     * Updates an existing note
     * @param nDTO DTO con los datos de la nota a actualizar.
     * @param request HttpServletRequest para obtener información de la solicitud.
     * @return DTO con el resultado de la operación de actualización.
     */
    @PutMapping(path = "/updateNota", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO updateNota(@RequestBody DTO nDTO, HttpServletRequest request) {
        DTO result = new DTO();

        try {
            int notaId = Integer.parseInt(nDTO.get("pId").toString());
            Nota notaToUpdate = nRep.findByPid(notaId);
            
            if (notaToUpdate != null) {
                notaToUpdate.setTitle(nDTO.get("title").toString());
                notaToUpdate.setSubtitle(nDTO.get("subtitle").toString());
                notaToUpdate.setContent(nDTO.get("content").toString());

                nRep.save(notaToUpdate);

                result.put("result", "success");
            } else {
                result.put("result", "No se encontró la nota con el ID proporcionado");
            }
        } catch (Exception e) {
            result.put("result", "No ha sido posible actualizar la nota: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

}
