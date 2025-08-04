package com.devjava.usuario.business;

import com.devjava.usuario.business.converter.UsuarioConverter;
import com.devjava.usuario.business.dto.UsuarioDTO;
import com.devjava.usuario.infrastructure.entity.Usuario;
import com.devjava.usuario.infrastructure.exceptions.ConflictException;
import com.devjava.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.devjava.usuario.infrastructure.repository.UsuarioRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    //Para salvar um usuario, recebi um objeto UsuarioDTO
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
    //Transformei ele em um usuario entity
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
    //Salvei a minha informação no BD, esse BD me deu um retorno que é um usuario entity
        usuario = usuarioRepository.save(usuario);
    //Desse usuario entity converti ele em um usuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email já cadastrado" + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return  usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
    }
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

}
