package com.devjava.usuario.business;

import com.devjava.usuario.business.converter.UsuarioConverter;
import com.devjava.usuario.business.dto.UsuarioDTO;
import com.devjava.usuario.infrastructure.entity.Usuario;
import com.devjava.usuario.infrastructure.repository.UsuarioRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    //Para salvar um usuario, recebi um objeto UsuarioDTO
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
    //Transformei ele em um usuario entity
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
    //Salvei a minha informação no BD, esse BD me deu um retorno que é um usuario entity
        usuario = usuarioRepository.save(usuario);
    //Desse usuario entity converti ele em um usuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

}
