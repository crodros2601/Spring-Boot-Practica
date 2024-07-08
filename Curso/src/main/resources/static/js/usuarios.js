// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios();
  $('#usuarios').DataTable();
  actualizarEmailDelUsuario();
});

function actualizarEmailDelUsuario(){
  document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarUsuarios(){
  const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: getHeaders()
  });

  const usuarios = await request.json();
  
  let listadoHTML = '';
  for (let usuario of usuarios) {

    let botonEliminar = '<a href="#" onClick="eliminarUsuario(' + usuario.id +')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
    
    let telefonoTexto = usuario.telefono == null ? '-' : usuario.telefono;

    let usuarioHTML = '<tr><td>' + usuario.id +'</td><td>' + usuario.nombre + ' ' + usuario.apellido + '</td><td>' 
                        + usuario.email + '</td><td>' 
                        + telefonoTexto + '</td><td>' + botonEliminar +'</td></tr>';
      listadoHTML += usuarioHTML;
  }

  console.log(usuarios);
    
  document.querySelector('#usuarios tbody').outerHTML = listadoHTML;

}

function getHeaders(){
  return {
    'Accept': 'application/json',
    'Content-Type': 'application/js	on',
    'Authorization': localStorage.token
  };
}

async function eliminarUsuario(id) {

  if(!confirm('¿Desea eliminar este usuario?')){
    return;
  }

  const request = await fetch('api/usuarios/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload();
}
