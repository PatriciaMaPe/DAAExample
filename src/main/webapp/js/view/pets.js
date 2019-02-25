var PetsView = (function() {
	var dao;
	
	// Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
	var self;
	
	var formId = 'pets-form';
	var listId = 'pets-list';
	var formQuery = '#' + formId;
	var listQuery = '#' + listId;
	
	var idOwner;
	
	function PetsView(petsDao, id, formContainerId, listContainerId) {
		
		dao = petsDao;
		idOwner = id;
		self = this;
		
		insertPetsForm($('#' + formContainerId));
		insertPetsList($('#' + listContainerId));
		
		this.init = function() {
			
			dao.listPetsOwner(idOwner,function(pets) {
				$.each(pets, function(key, pet) {
					appendToTable(pet);
				});
			});
			/*dao.listPets(function(pets) {
				$.each(pets, function(key, pet) {
					appendToTable(pet);
				});
			});*/
			
			// La acción por defecto de enviar formulario (submit) se sobreescribe
			// para que el envío sea a través de AJAX
			$(formQuery).submit(function(event) {
				var pet = self.getPetInForm();
				
				if (self.isEditing()) {
					dao.modifyPet(pet,
						function(pet) {
							$('#pet-' + pet.id + ' td.name').text(pet.name);
							$('#pet-' + pet.species + ' td.species').text(pet.species);
							$('#pet-' + pet.breed + ' td.breed').text(pet.breed);
							$('#pet-' + pet.owner + ' td.owner').text(pet.owner);
							self.resetForm();
						},
						showErrorMessage,
						self.enableForm
					);
				} else {
					dao.addPet(pet,
						function(pet) {
							appendToTable(pet);
							self.resetForm();
						},
						showErrorMessage,
						self.enableForm
					);
				}
				
				return false;
			});
			
			$('#btnClear').click(this.resetForm);
		};

		this.getPetInForm = function() {
			var form = $(formQuery);
			return {
				'id': form.find('input[name="id"]').val(),
				'name': form.find('input[name="name"]').val(),
				'species': form.find('input[name="species"]').val(),
				'breed': form.find('input[name="breed"]').val(),
				'owner': form.find('input[name="owner"]').val()
			};
		};

		this.getPetInRow = function(id) {
			var row = $('#pet-' + id);

			if (row !== undefined) {
				return {
					'id': id,
					'name': row.find('td.name').text(),
					'species': row.find('td.species').text(),
					'breed': row.find('td.breed').text(),
					'owner': row.find('td.owner').text()
					
				};
			} else {
				return undefined;
			}
		};
		
		this.editPet = function(id) {
			var row = $('#pet-' + id);

			if (row !== undefined) {
				var form = $(formQuery);
				
				form.find('input[name="id"]').val(id);
				form.find('input[name="name"]').val(row.find('td.name').text());
				form.find('input[name="species"]').val(row.find('td.species').text());
				form.find('input[name="breed"]').val(row.find('td.breed').text());
				form.find('input[name="owner"]').val(row.find('td.owner').text());
				
				$('input#btnSubmit').val('Modificar');
			}
		};
		
		this.deletePet = function(id) {
			if (confirm('Está a punto de eliminar a una mascota. ¿Está seguro de que desea continuar?')) {
				dao.deletePet(id,
					function() {
						$('tr#pet-' + id).remove();
					},
					showErrorMessage
				);
			}
		};

		this.isEditing = function() {
			return $(formQuery + ' input[name="id"]').val() != "";
		};

		this.disableForm = function() {
			$(formQuery + ' input').prop('disabled', true);
		};

		this.enableForm = function() {
			$(formQuery + ' input').prop('disabled', false);
		};

		this.resetForm = function() {
			$(formQuery)[0].reset();
			$(formQuery + ' input[name="id"]').val('');
			$('#btnSubmit').val('Crear');
		};
	};
	
	var insertPetsList = function(parent) {
		parent.append(
			'<div class="table-responsive">\
				<table id="' + listId + '" class="table">\
				<thead>\
					<tr>\
						<th>Nombre</th>\
						<th>Especie</th>\
						<th>Raza</th>\
						<th>Dueño</th>\
						<th>&nbsp;</th>\
					</tr>\
				</thead>\
				<tbody>\
				</tbody>\
			</table></div>'
		);
	};

	var insertPetsForm = function(parent) {
		parent.append(
			'<form id="' + formId + '" class="mb-5 mb-10">\
				<input name="id" type="hidden" value=""/>\
				<div class="row">\
					<div class="col-sm-2">\
						<input name="name" type="text" value="" placeholder="Nombre" class="form-control" required/>\
					</div>\
					<div class="col-sm-2">\
						<input name="species" type="text" value="" placeholder="Especie" class="form-control" required/>\
					</div>\
					<div class="col-sm-2">\
						<input name="breed" type="text" value="" placeholder="Raza" class="form-control" required/>\
					</div>\
					<div class="col-sm-3">\
						<input name="owner" type="text" value="" placeholder="Dueño" class="form-control" required/>\
					</div>\
					<div class="col-sm-3">\
						<input id="btnSubmit" type="submit" value="Crear" class="btn btn-primary" />\
						<input id="btnClear" type="reset" value="Limpiar" class="btn" />\
					</div>\
				</div>\
			</form>'
		);
	};

	var createPetRow = function(pet) {
		return '<tr id="pet-'+ pet.id +'"">\
			<td>' + pet.name + '</td>\
			<td>' + pet.species + '</td>\
			<td>' + pet.breed + '</td>\
			<td>' + pet.owner + '</td>\
			<td>\
				<a class="edit btn btn-primary" href="#">Editar</a>\
				<a class="delete btn btn-warning" href="#">Eliminar</a>\
			</td>\
		</tr>';
	};

	var showErrorMessage = function(jqxhr, textStatus, error) {
		alert(textStatus + ": " + error);
	};

	var addRowListeners = function(pet) {
		$('#pet-' + pet.id + ' a.edit').click(function() {
			self.editPet(pet.id);
		});
		
		$('#pet-' + pet.id + ' a.delete').click(function() {
			self.deletePet(pet.id);
		});
	};

	var appendToTable = function(pet) {
		$(listQuery + ' > tbody:last')
			.append(createPetRow(pet));
		addRowListeners(pet);
	};
	
	return PetsView;
})();
