
package com.crisdev.usuario.business;

import com.crisdev.usuario.business.converter.UsuarioConverter;
import com.crisdev.usuario.business.dto.UsuarioDTO;
import com.crisdev.usuario.infrastructure.entity.Usuario;
import com.crisdev.usuario.infrastructure.exceptions.ConflictException;
import com.crisdev.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.crisdev.usuario.infrastructure.repository.UsuarioRepository;
import com.crisdev.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
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

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
       // Buscar o email do usuário atraveis do token (sem a obrigatoriedade do email)
       String email = jwtUtil.extrairEmailDoToken(token.substring(7));

       // Criptografia de senha
       dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

       // Busca os dados do usuário no banco de dados
       Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(()->
               new ResourceNotFoundException("Email não localizado"));

       // Mescla os dados que recebemos na requisição DTO com dados do banco de dados
       Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

       // Salvou os dados do usuario convertido e depois pegou o retorno e converteu para UsuarioDTO
       return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
