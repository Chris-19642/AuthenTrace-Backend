package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.FirmaDTO;
import com.upc.appauthentrace.entidades.Firma;
import com.upc.appauthentrace.interfaces.IFirmaServicio;
import com.upc.appauthentrace.repositorios.FirmaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirmaServicio implements IFirmaServicio {

    @Autowired
    private FirmaRepositorio firmaRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Utilidad para calcular hash SHA-256 de la firma
    private String calcularHash(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular hash", e);
        }
    }

    @Override
    public String registrarFirmaBase(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String hash = calcularHash(bytes);

            if (firmaRepository.existsByHashFirma(hash)) {
                return "La firma ya existe en la base de datos";
            }

            Firma firma = new Firma();
            firma.setFirma(bytes);
            firma.setHashFirma(hash);
            firma.setFechaRegistro(Instant.now());

            firmaRepository.save(firma);
            return "Firma registrada correctamente";
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la firma", e);
        }
    }

    @Override
    public List<FirmaDTO> listarFirmas() {
        return firmaRepository.findAll().stream()
                .map(f -> modelMapper.map(f, FirmaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarFirma(Long idFirma) {
        firmaRepository.deleteById(idFirma);
    }
}
