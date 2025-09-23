package com.upc.appauthentrace.service;

import com.upc.appauthentrace.dto.DocumentoDTO;
import com.upc.appauthentrace.entidades.Documento;
import com.upc.appauthentrace.entidades.Usuario;
import com.upc.appauthentrace.interfaces.IDocumentoServicio;
import com.upc.appauthentrace.repositorios.DocumentoRepositorio;
import com.upc.appauthentrace.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoServicio implements IDocumentoServicio {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    private final String UPLOAD_DIR = "uploads/"; // definitiva
    private final String TEMP_DIR = "temp/"; // temporal

    @Override
    public String subirDocumentoTemporal(MultipartFile file) {
        try {
            File carpeta = new File(TEMP_DIR);
            if (!carpeta.exists()) carpeta.mkdirs();

            String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path ruta = Paths.get(TEMP_DIR + nombreArchivo);

            Files.write(ruta, file.getBytes());
            return nombreArchivo; // solo se devuelve el nombre para luego decidir
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo temporal", e);
        }
    }

    @Override
    public boolean cancelarDocumentoTemporal(String nombreArchivo) {
        Path ruta = Paths.get(TEMP_DIR + nombreArchivo);
        try {
            return Files.deleteIfExists(ruta);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el archivo temporal", e);
        }
    }

    @Override
    public DocumentoDTO guardarDefinitivo(String nombreArchivo, Long idUsuario) {
        try {
            Path rutaTemp = Paths.get(TEMP_DIR + nombreArchivo);
            if (!Files.exists(rutaTemp)) {
                throw new RuntimeException("El archivo temporal no existe");
            }

            File carpeta = new File(UPLOAD_DIR);
            if (!carpeta.exists()) carpeta.mkdirs();

            Path rutaFinal = Paths.get(UPLOAD_DIR + nombreArchivo);
            Files.move(rutaTemp, rutaFinal); // mover archivo

            Usuario usuario = usuarioRepositorio.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Documento documento = new Documento();
            documento.setUsuario(usuario);
            documento.setNombre(nombreArchivo);
            documento.setTipoDocumento(Files.probeContentType(rutaFinal));
            documento.setRutaArchivo(rutaFinal.toString());
            documento.setFechaSubida(Instant.now());

            Documento guardado = documentoRepositorio.save(documento);
            return modelMapper.map(guardado, DocumentoDTO.class);

        } catch (IOException e) {
            throw new RuntimeException("Error al mover archivo a definitiva", e);
        }
    }

    @Override
    public List<DocumentoDTO> listarDocumentos() {
        return documentoRepositorio.findAll().stream()
                .map(doc -> modelMapper.map(doc, DocumentoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentoDTO> listarPorUsuario(Long idUsuario) {
        return documentoRepositorio.findByUsuarioIdUsuario(idUsuario).stream()
                .map(doc -> modelMapper.map(doc, DocumentoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarDocumento(Long idDocumento) {
        documentoRepositorio.deleteById(idDocumento);
    }
}

