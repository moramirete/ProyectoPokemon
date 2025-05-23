-- Creacion de base de datos
CREATE DATABASE pokemon;

-- Seleccionar la base de datos
USE pokemon;

-- Tabla ENTRENADOR
CREATE TABLE ENTRENADOR (
    ID_ENTRENADOR INT PRIMARY KEY,
    USUARIO VARCHAR(20) NOT NULL UNIQUE,
    CONTRASENA VARCHAR(20) NOT NULL,
    POKEDOLARES INT DEFAULT 1000
);

-- Tabla OBJETO
CREATE TABLE OBJETO (
    ID_OBJETO INT PRIMARY KEY,
    NOM_OBJETO VARCHAR(50) NOT NULL,
    ATAQUE INT,
    DEFENSA INT,
    AT_ESPECIAL INT,
    DEF_ESPECIAL INT,
    VELOCIDAD INT,
    VITALIDAD INT,
    PP INT,
    PRECIO INT NOT NULL
);

-- Tabla MOCHILA
CREATE TABLE MOCHILA (
    ID_ENTRENADOR INT,
    ID_OBJETO INT,
    CANTIDAD INT NOT NULL,
    PRIMARY KEY (ID_ENTRENADOR, ID_OBJETO),
    FOREIGN KEY (ID_ENTRENADOR) REFERENCES ENTRENADOR(ID_ENTRENADOR),
    FOREIGN KEY (ID_OBJETO) REFERENCES OBJETO(ID_OBJETO)
);

-- Tabla POKEDEX
CREATE TABLE POKEDEX (
    NUM_POKEDEX INT PRIMARY KEY,
    NOM_POKEMON VARCHAR(20) NOT NULL,
    IMG_FRONTAL VARCHAR(50),
    IMG_TRASERA VARCHAR(50),
    SPRITES_FRONTAL VARCHAR(50),
    SPRITES_TRASEROS VARCHAR(50),
    SONIDO VARCHAR(100),
    NIVEL_EVOLUCION INT,
    TIPO1 VARCHAR(20) NOT NULL CHECK (TIPO1 IN('AGUA', 'BICHO','DRAGON','ELECTRICO','FANTASMA','FUEGO','HIELO','LUCHA','NORMAL','PLANTA','PSIQUICO','ROCA','TIERRA','VENENO','VOLADOR')),
    TIPO2 VARCHAR(20)
);

-- Tabla POKEMON
CREATE TABLE POKEMON (
    ID_POKEMON INT PRIMARY KEY,
    ID_ENTRENADOR INT,
    NUM_POKEDEX INT,
    ID_OBJETO INT NULL,
    NOM_POKEMON VARCHAR(20) NOT NULL,
    VITALIDAD INT NOT NULL,
    ATAQUE INT NOT NULL,
    DEFENSA INT NOT NULL,
    AT_ESPECIAL INT NOT NULL,
    DEF_ESPECIAL INT NOT NULL,
    VELOCIDAD INT NOT NULL,
    NIVEL INT NOT NULL,
    FERTILIDAD INT NOT NULL DEFAULT 5,
    SEXO CHAR(1) NOT NULL CHECK (SEXO IN ('M', 'F')),
    ESTADO VARCHAR(30) NOT NULL CHECK (ESTADO IN ('NORMAL', 'PARALIZADO', 'QUEMADO', 'ENVENENADO', 'GRAVEMENTE ENVENENADO', 'DORMIDO', 'CONGELADO', 'HELADO', 'SOMNOLIENTO', 'POKERUS', 'DEBILITADO', 'CONFUSO', 'ENAMORADO', 'ATRAPADO', 'MALDITO', 'DRENADORAS', 'CANTO MORTAL', 'CENTRO DE ATENCION', 'AMEDRENTADO')),
    EQUIPO INT NOT NULL CHECK (EQUIPO IN (1, 2, 3)),
    FOREIGN KEY (ID_ENTRENADOR) REFERENCES ENTRENADOR(ID_ENTRENADOR),
    FOREIGN KEY (NUM_POKEDEX) REFERENCES POKEDEX(NUM_POKEDEX),
    FOREIGN KEY (ID_OBJETO) REFERENCES OBJETO(ID_OBJETO) ON DELETE SET NULL
);


-- Cambios realizados dia 17/04/2025
ALTER TABLE POKEMON ADD CONSTRAINT chk_sexo CHECK (SEXO IN ('M', 'F'));
ALTER TABLE POKEMON MODIFY COLUMN SEXO CHAR(1) NOT NULL DEFAULT 'M';

-- Cambios realizados dia 21/04/2025
ALTER TABLE POKEMON
ADD TIPO1 VARCHAR(20) NOT NULL CHECK (TIPO1 IN ('AGUA', 'BICHO', 'DRAGON', 'ELECTRICO', 'FANTASMA', 'FUEGO', 'HIELO', 'LUCHA', 'NORMAL', 'PLANTA', 'PSIQUICO', 'ROCA', 'TIERRA', 'VENENO', 'VOLADOR'));
ALTER TABLE POKEMON
ADD TIPO2 VARCHAR(20) CHECK (TIPO2 IN ('AGUA', 'BICHO', 'DRAGON', 'ELECTRICO', 'FANTASMA', 'FUEGO', 'HIELO', 'LUCHA', 'NORMAL', 'PLANTA', 'PSIQUICO', 'ROCA', 'TIERRA', 'VENENO', 'VOLADOR'));

-- Cambios realizados dia 13/05/2025
ALTER TABLE POKEMON
ADD VITALIDAD_OBJ INT NOT NULL,
ADD ATAQUE_OBJ INT NOT NULL,
ADD DEFENSA_OBJ INT NOT NULL,
ADD AT_ESPECIAL_OBJ INT NOT NULL,
ADD DEF_ESPECIAL_OBJ INT NOT NULL,
ADD VELOCIDAD_OBJ INT NOT NULL,
ADD VITALIDADMAX_OBJ INT NOT NULL,
ADD EXPERIENCIA INT DEFAULT 0;

-- Tabla MOVIMIENTO
CREATE TABLE MOVIMIENTO(
    ID_MOVIMIENTO INT PRIMARY KEY,
    NOM_MOVIMIENTO VARCHAR(20) NOT NULL UNIQUE,
    DESCRIPCION VARCHAR(100) NOT NULL,
    PRECI INT(3) NOT NULL,
    PP_MAX INT(2) NOT NULL,
    TIPO VARCHAR(20) CHECK (TIPO IN('ATAQUE', 'ESTADO','MEJORA')),
    TIPO_MOV VARCHAR(30) CHECK (TIPO_MOV IN('AGUA','BICHO','DRAGON','ELECTRICO','FANTASMA','FUEGO','HIELO','LUCHA','NORMAL','PLANTA','PSIQUICO','ROCA','TIERRA','VENENO','VOLADOR')),
    -- ATAQUE
    POTENCIA INT,
    -- ESTADO
    ESTADO VARCHAR(30) CHECK (ESTADO IN ('NORMAL', 'PARALIZADO', 'QUEMADO', 'ENVENENADO', 'GRAVEMENTE ENVENENADO', 'DORMIDO', 'CONGELADO', 'HELADO', 'SOMNOLIENTO', 'POKERUS', 'DEBILITADO', 'CONFUSO', 'ENAMORADO', 'ATRAPADO', 'MALDITO', 'DRENADORAS', 'CANTO MORTAL', 'CENTRO DE ATENCION', 'AMEDRENTADO')),
    TURNOS INT(1),
    -- MEJORA
    MEJORA VARCHAR(20),
    NUM_MOV INT(1) NOT NULL, -- Todos los movimientos tienen que tener al menos 1 (que es lo que dura)
    CANT_MEJORA INT(3)
);

-- Cambios realizados dia 13/05/2025
CREATE TABLE MOVIMIENTO_POKEMON (
    ID_ENTRENADOR INT,
    ID_MOVIMIENTO INT,
    ID_POKEMON INT,
    PP_ACTUALES INT,
    POSICION INT NOT NULL CHECK (POSICION IN (1, 2, 3, 4, 5)),
    
    PRIMARY KEY (ID_ENTRENADOR, ID_MOVIMIENTO, ID_POKEMON),
    
    CONSTRAINT fk_movimiento_entrenador FOREIGN KEY (ID_ENTRENADOR) REFERENCES ENTRENADOR(ID_ENTRENADOR),
    CONSTRAINT fk_movimiento_movimiento FOREIGN KEY (ID_MOVIMIENTO) REFERENCES MOVIMIENTO(ID_MOVIMIENTO),
    CONSTRAINT fk_movimiento_pokemon FOREIGN KEY (ID_POKEMON) REFERENCES POKEMON(ID_POKEMON)
);

INSERT INTO MOVIMIENTO (ID_MOVIMIENTO, NOM_MOVIMIENTO, DESCRIPCION, PRECI, PP_MAX, TIPO, TIPO_MOV, POTENCIA, ESTADO, TURNOS, MEJORA, NUM_MOV, CANT_MEJORA) VALUES 
(0001, 'Destructor', 'Golpea al objetivo con las extremidades, la cola o similares.', 100, 35, 'ATAQUE', 'NORMAL', 40, NULL, 1, NULL, 0, NULL),
(0002, 'Golpe Karate', 'Da un golpe cortante. Suele ser crítico.', 100, 25, 'ATAQUE', 'LUCHA', 50, NULL, 1, NULL, 0, NULL),
(0003, 'Doble Bofetón', 'Abofetea de dos a cinco veces seguidas.', 85, 15, 'ATAQUE', 'NORMAL', 15, NULL, 1, NULL, 0, NULL),
(0004, 'Puño Cometa', 'Pega de dos a cinco veces seguidas.', 85, 15, 'ATAQUE', 'NORMAL', 18, NULL, 1, NULL, 0, NULL),
(0005, 'Megapuño', 'Puñetazo de gran potencia.', 85, 20, 'ATAQUE', 'NORMAL', 80, NULL, 1, NULL, 0, NULL),
(0006, 'Día de Pago', 'Ataca al objetivo arrojándole monedas. Al finalizar el combate, las recupera en forma de ganancias.', 100, 20, 'ATAQUE', 'NORMAL', 40, NULL, 1, NULL, 0, NULL),
(0007, 'Puño Fuego', 'Puñetazo ardiente que puede causar quemaduras.', 100, 15, 'ATAQUE', 'FUEGO', 75, NULL, 1, NULL, 0, NULL),
(0008, 'Puño Hielo', 'Puñetazo helado que puede llegar a congelar.', 100, 15, 'ATAQUE', 'HIELO', 75, NULL, 1, NULL, 0, NULL),
(0009, 'Puño Trueno', 'Puñetazo eléctrico que puede paralizar.', 100, 15, 'ATAQUE', 'ELECTRICO', 75, NULL, 1, NULL, 0, NULL),
(0010, 'Arañazo', 'Araña con afiladas garras.', 100, 35, 'ATAQUE', 'NORMAL', 40, NULL, 1, NULL, 0, NULL),
(0011, 'Agarre', 'Atenaza al objetivo y le inflige daño.', 100, 30, 'ATAQUE', 'NORMAL', 55, NULL, 1, NULL, 0, NULL),
(0012, 'Guillotina', 'Ataque cortante con grandes pinzas que fulmina al objetivo de un solo golpe si acierta.', 30, 5, 'ATAQUE', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0013, 'Viento Cortante', 'Primero se prepara y en el segundo turno ataca al oponente con ráfagas de viento cortante. Alta probabilidad de ser crítico.', 100, 10, 'ATAQUE', 'NORMAL', 80, NULL, 2, NULL, 0, NULL),
(0014, 'Danza Espada', 'Este frenético baile de combate eleva el espíritu y aumenta mucho el Ataque.', 100, 30, 'MEJORA', 'NORMAL', 0, NULL, 1, 'ATAQUE+', 0, 2),
(0015, 'Corte', 'Corta al objetivo con garras, guadañas o similares.', 95, 30, 'ATAQUE', 'NORMAL', 50, NULL, 1, NULL, 0, NULL),
(0016, 'Tornado', 'Crea un tornado con las alas y lo lanza contra el objetivo.', 100, 35, 'ATAQUE', 'VOLADOR', 40, NULL, 1, NULL, 0, NULL),
(0017, 'Ataque Ala', 'Extiende totalmente sus majestuosas alas para golpear al objetivo con ellas.', 100, 35, 'ATAQUE', 'VOLADOR', 60, NULL, 1, NULL, 0, NULL),
(0018, 'Remolino', 'Se lleva al objetivo, que es cambiado por otro Pokémon. Si es un Pokémon salvaje, acaba el combate.', 85, 20, 'ESTADO', 'NORMAL', 0, 'ATRAPADO', 1, NULL, 0, NULL),
(0019, 'Vuelo', 'El usuario vuela en el primer turno y ataca en el segundo.', 95, 15, 'ATAQUE', 'VOLADOR', 90, NULL, 2, NULL, 0, NULL),
(0020, 'Atadura', 'Usa el cuerpo o las extremidades para atar y oprimir al objetivo e infligirle daño de cuatro a cinco turnos.', 85, 20, 'ATAQUE', 'NORMAL', 15, 'ATRAPADO', 4, NULL, 0, NULL),
(0021, 'Latigazo', 'Golpea con la cola o con lianas, por ejemplo, para causar daño al objetivo.', 75, 20, 'ATAQUE', 'NORMAL', 80, NULL, 1, NULL, 0, NULL),
(0022, 'Látigo Cepa', 'Azota al objetivo con lianas delgadas y largas tan flexibles como látigos.', 100, 25, 'ATAQUE', 'PLANTA', 45, NULL, 1, NULL, 0, NULL),
(0023, 'Pisotón', 'Tremendo pisotón que puede hacer que el objetivo se amedrente.', 100, 20, 'ATAQUE', 'NORMAL', 65, 'AMEDRENTADO', 1, NULL, 0, NULL),
(0024, 'Doble Patada', 'Propina dos patadas seguidas, una con cada pie.', 100, 30, 'ATAQUE', 'LUCHA', 30, NULL, 1, NULL, 0, NULL),
(0025, 'Megapatada', 'Patada de extrema fuerza.', 75, 5, 'ATAQUE', 'NORMAL', 120, NULL, 1, NULL, 0, NULL),
(0026, 'Patada Salto', 'Da un salto y pega una patada. Si falla, se lesiona.', 95, 10, 'ATAQUE', 'LUCHA', 100, NULL, 1, NULL, 0, NULL),
(0027, 'Patada Giro', 'Da una patada rápida y circular. Puede hacer retroceder al objetivo.', 85, 15, 'ATAQUE', 'LUCHA', 60, NULL, 1, NULL, 0, NULL),
(0028, 'Ataque Arena', 'Arroja arena a la cara del objetivo y reduce su Precisión.', 100, 15, 'ESTADO', 'NORMAL', 0, NULL, 1, 'PRECISION-', 0, 1),
(0029, 'Golpe Cabeza', 'Potente cabezazo que puede amedrentar al objetivo.', 100, 15, 'ATAQUE', 'NORMAL', 70, 'AMEDRENTADO', 1, NULL, 0, NULL),
(0030, 'Cornada', 'Ataca al objetivo con una cornada punzante.', 100, 25, 'ATAQUE', 'NORMAL', 65, NULL, 1, NULL, 0, NULL),
(0031, 'Ataque Furia', 'Cornea o picotea al objetivo de dos a cinco veces seguidas.', 85, 20, 'ATAQUE', 'NORMAL', 15, NULL, 1, NULL, 0, NULL),
(0032, 'Perforador', 'Ataque con un cuerno giratorio que fulmina al objetivo de un solo golpe si acierta.', 30, 5, 'ATAQUE', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0033, 'Placaje', 'Embestida con todo el cuerpo.', 100, 35, 'ATAQUE', 'NORMAL', 40, NULL, 1, NULL, 0, NULL),
(0034, 'Golpe Cuerpo', 'Salta sobre el objetivo con todo su peso y puede llegar a paralizarlo.', 100, 15, 'ATAQUE', 'NORMAL', 85, 'PARALIZADO', 1, NULL, 0, NULL),
(0035, 'Constricción', 'Oprime al objetivo de cuatro a cinco turnos con lianas o con su cuerpo.', 90, 20, 'ATAQUE', 'NORMAL', 15, 'ATRAPADO', 4, NULL, 0, NULL),
(0036, 'Derribo', 'Carga desmedida que también hiere ligeramente al usuario.', 85, 20, 'ATAQUE', 'NORMAL', 90, NULL, 1, NULL, 0, NULL),
(0037, 'Saña', 'El usuario ataca enfurecido durante dos o tres turnos y, después, se queda confuso.', 100, 10, 'ATAQUE', 'NORMAL', 120, 'CONFUSO', 2, NULL, 0, NULL),
(0038, 'Doble Filo', 'Ataque arriesgado que también hiere seriamente al usuario.', 100, 15, 'ATAQUE', 'NORMAL', 120, NULL, 1, NULL, 0, NULL),
(0039, 'Látigo', 'Agita la cola para despistar al objetivo y reducir su Defensa.', 100, 30, 'ESTADO', 'NORMAL', 0, NULL, 1, 'DEFENSA-', 0, 1),
(0040, 'Picotazo Veneno', 'Lanza un aguijón tóxico que puede envenenar al objetivo.', 100, 35, 'ATAQUE', 'VENENO', 15, 'ENVENENADO', 1, NULL, 0, NULL),
(0041, 'Doble Ataque', 'Pincha dos veces con dos espinas. Puede envenenar.', 100, 20, 'ATAQUE', 'BICHO', 25, 'ENVENENADO', 1, NULL, 0, NULL),
(0042, 'Pin Misil', 'Lanza afiladas púas que hieren al objetivo de dos a cinco veces seguidas.', 95, 20, 'ATAQUE', 'BICHO', 25, NULL, 1, NULL, 0, NULL),
(0043, 'Malicioso', 'Intimida al objetivo con una mirada torva para reducir su Defensa.', 100, 30, 'ESTADO', 'NORMAL', 0, NULL, 1, 'DEFENSA-', 0, 1),
(0044, 'Mordisco', 'Un voraz bocado con dientes afilados que puede amedrentar al objetivo.', 100, 25, 'ATAQUE', 'NORMAL', 60, 'AMEDRENTADO', 1, NULL, 0, NULL),
(0045, 'Gruñido', 'Dulce gruñido que distrae al objetivo para que baje la guardia y reduce su Ataque.', 100, 40, 'ESTADO', 'NORMAL', 0, NULL, 1, 'ATAQUE-', 0, 1),
(0046, 'Rugido', 'Se lleva al objetivo, que es cambiado por otro Pokémon. Si es un Pokémon salvaje, acaba el combate.', 100, 20, 'ESTADO', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0047, 'Canto', 'Cantinela que hace dormir profundamente al objetivo.', 55, 15, 'ESTADO', 'NORMAL', 0, 'DORMIDO', 1, NULL, 0, NULL),
(0048, 'Supersónico', 'El cuerpo del usuario emite unas ondas sónicas raras que confunden al objetivo.', 55, 20, 'ESTADO', 'NORMAL', 0, 'CONFUSO', 1, NULL, 0, NULL),
(0049, 'Bomba Sónica', 'Lanza ondas de choque que restan 20 PS al objetivo.', 90, 20, 'ATAQUE', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0050, 'Anulación', 'Anula el último movimiento usado por el objetivo durante cuatro turnos.', 100, 20, 'ESTADO', 'NORMAL', 0, NULL, 4, NULL, 0, NULL),
(0051, 'Ácido', 'Rocía al objetivo con un ácido corrosivo. Puede reducir la Defensa Especial.', 100, 30, 'ATAQUE', 'VENENO', 40, NULL, 1, NULL, 0, NULL),
(0052, 'Ascuas', 'Ataca con llamas pequeñas que pueden causar quemaduras.', 100, 25, 'ATAQUE', 'FUEGO', 40, 'QUEMADO', 1, NULL, 0, NULL),
(0053, 'Lanzallamas', 'Ataca con una gran ráfaga de fuego que puede causar quemaduras.', 100, 15, 'ATAQUE', 'FUEGO', 90, 'QUEMADO', 1, NULL, 0, NULL),
(0054, 'Neblina', 'Rodea de una niebla blanquecina al bando del usuario e impide que el rival reduzca sus características durante cinco turnos.', 100, 30, 'ESTADO', 'HIELO', 0, NULL, 5, NULL, 0, NULL),
(0055, 'Pistola Agua', 'Ataca disparando agua con gran potencia.', 100, 25, 'ATAQUE', 'AGUA', 40, NULL, 1, NULL, 0, NULL),
(0056, 'Hidrobomba', 'Lanza una gran masa de agua a presión para atacar.', 80, 5, 'ATAQUE', 'AGUA', 110, NULL, 1, NULL, 0, NULL),
(0057, 'Surf', 'Inunda el terreno de combate con una ola gigante que golpea a los Pokémon adyacentes.', 100, 15, 'ATAQUE', 'AGUA', 90, NULL, 1, NULL, 0, NULL),
(0058, 'Rayo Hielo', 'Rayo de hielo que puede llegar a congelar.', 100, 10, 'ATAQUE', 'HIELO', 90, 'CONGELADO', 1, NULL, 0, NULL),
(0059, 'Ventisca', 'Tormenta de hielo que puede llegar a congelar.', 70, 5, 'ATAQUE', 'HIELO', 110, 'CONGELADO', 1, NULL, 0, NULL),
(0060, 'Psicorrayo', 'Extraño rayo que puede causar confusión.', 100, 20, 'ATAQUE', 'PSIQUICO', 65, 'CONFUSO', 1, NULL, 0, NULL),
(0061, 'Rayo Burbuja', 'Ráfaga de burbujas que puede reducir la Velocidad.', 100, 20, 'ATAQUE', 'AGUA', 65, NULL, 1, NULL, 0, NULL),
(0062, 'Rayo Aurora', 'Rayo multicolor que puede reducir el Ataque.', 100, 20, 'ATAQUE', 'HIELO', 65, NULL, 1, NULL, 0, NULL),
(0063, 'Hiperrayo', 'El usuario ataca al objetivo con un potente haz de luz, pero deberá descansar en el siguiente turno.', 90, 5, 'ATAQUE', 'NORMAL', 150, NULL, 2, NULL, 0, NULL),
(0064, 'Picotazo', 'Ensarta al objetivo con un cuerno o pico punzante.', 100, 35, 'ATAQUE', 'VOLADOR', 35, NULL, 1, NULL, 0, NULL),
(0065, 'Pico Taladro', 'Picotazo giratorio y perforador muy potente.', 100, 20, 'ATAQUE', 'VOLADOR', 80, NULL, 1, NULL, 0, NULL),
(0066, 'Sumisión', 'El usuario se lanza al suelo con el oponente en brazos y también se hace un poco de daño.', 80, 20, 'ATAQUE', 'LUCHA', 80, NULL, 1, NULL, 0, NULL),
(0067, 'Patada Baja', 'Patada baja que derriba al objetivo.', 100, 20, 'ATAQUE', 'LUCHA', 0, NULL, 1, NULL, 0, NULL),
(0068, 'Contraataque', 'Con más peso este, más daño le causa.', 100, 20, 'ATAQUE', 'LUCHA', 0, NULL, 1, NULL, 0, NULL),
(0069, 'Sísmico', 'Aprovecha la gravedad para derribar al objetivo. Le resta tantos PS como nivel tenga el usuario.', 100, 20, 'ATAQUE', 'LUCHA', 0, NULL, 1, NULL, 0, NULL),
(0070, 'Fuerza', 'Ataca al objetivo golpeándolo con todas sus fuerzas.', 100, 15, 'ATAQUE', 'NORMAL', 80, NULL, 1, NULL, 0, NULL),
(0071, 'Absorber', 'Un ataque que absorbe nutrientes. El usuario recupera la mitad de los PS del daño que produce.', 100, 25, 'ATAQUE', 'PLANTA', 20, NULL, 1, NULL, 0, NULL),
(0072, 'Megaagotar', 'Un ataque que absorbe nutrientes. El usuario recupera la mitad de los PS del daño que produce.', 100, 15, 'ATAQUE', 'PLANTA', 40, NULL, 1, NULL, 0, NULL),
(0073, 'Drenadoras', 'Planta semillas que absorben PS del objetivo en cada turno y que le sirven para recuperarse.', 90, 10, 'ESTADO', 'PLANTA', 0, 'DRENADORAS', 5, NULL, 0, NULL),
(0074, 'Desarrollo', 'Hace que su cuerpo crezca a marchas forzadas, con lo que aumenta su Ataque y su Ataque Especial.', 0, 20, 'MEJORA', 'NORMAL', 0, NULL, 1, 'ATAQUE+', 0, 1),
(0075, 'Hoja Afilada', 'Corta con hojas afiladas. Suele asestar un golpe crítico.', 95, 25, 'ATAQUE', 'PLANTA', 55, NULL, 1, NULL, 0, NULL),
(0076, 'Rayo Solar', 'El usuario absorbe luz en el primer turno y, en el segundo, lanza un potente rayo de energía.', 100, 10, 'ATAQUE', 'PLANTA', 120, NULL, 2, NULL, 0, NULL),
(0077, 'Polvo Veneno', 'Esparce polvo tóxico que envenena al objetivo.', 75, 35, 'ESTADO', 'VENENO', 0, 'ENVENENADO', 1, NULL, 0, NULL),
(0078, 'Paralizador', 'Esparce polvo que paraliza al objetivo.', 75, 30, 'ESTADO', 'PLANTA', 0, 'PARALIZADO', 1, NULL, 0, NULL),
(0079, 'Somnífero', 'Esparce polvo que duerme al objetivo.', 75, 15, 'ESTADO', 'PLANTA', 0, 'DORMIDO', 1, NULL, 0, NULL),
(0080, 'Danza Pétalo', 'Ataca al objetivo lanzando pétalos de dos a tres turnos y, al finalizar, el usuario se queda confuso.', 100, 10, 'ATAQUE', 'PLANTA', 120, 'CONFUSO', 2, NULL, 0, NULL),
(0081, 'Disparo Demora', 'Lanza seda por la boca al objetivo y reduce mucho su Velocidad.', 95, 40, 'ESTADO', 'BICHO', 0, NULL, 1, 'VELOCIDAD-', 0, 1),
(0082, 'Furia Dragón', 'Ráfaga de furiosas ondas de choque que quitan 40 PS.', 100, 10, 'ATAQUE', 'DRAGON', 0, NULL, 1, NULL, 0, NULL),
(0083, 'Giro Fuego', 'Un anillo de fuego que atrapa y daña al objetivo de cuatro a cinco turnos.', 85, 15, 'ATAQUE', 'FUEGO', 35, 'ATRAPADO', 4, NULL, 0, NULL),
(0084, 'Impactrueno', 'Ataque eléctrico que puede paralizar al objetivo.', 100, 30, 'ATAQUE', 'ELECTRICO', 40, 'PARALIZADO', 1, NULL, 0, NULL),
(0085, 'Rayo', 'Potente ataque eléctrico que puede paralizar al objetivo.', 100, 15, 'ATAQUE', 'ELECTRICO', 90, 'PARALIZADO', 1, NULL, 0, NULL),
(0086, 'Onda Trueno', 'Una ligera descarga que paraliza al objetivo.', 90, 20, 'ESTADO', 'ELECTRICO', 0, 'PARALIZADO', 1, NULL, 0, NULL),
(0087, 'Trueno', 'Un poderoso rayo que daña al objetivo y puede paralizarlo.', 70, 10, 'ATAQUE', 'ELECTRICO', 110, 'PARALIZADO', 1, NULL, 0, NULL),
(0088, 'Lanzarrocas', 'Tira una pequeña roca al objetivo.', 90, 15, 'ATAQUE', 'ROCA', 50, NULL, 1, NULL, 0, NULL),
(0089, 'Terremoto', 'Un terremoto que afecta a los Pokémon adyacentes.', 100, 10, 'ATAQUE', 'TIERRA', 100, NULL, 1, NULL, 0, NULL),
(0090, 'Fisura', 'Abre una grieta en el suelo y mete al objetivo en ella. Fulmina al objetivo de un solo golpe si acierta.', 30, 5, 'ATAQUE', 'TIERRA', 0, NULL, 1, NULL, 0, NULL),
(0091, 'Excavar', 'El usuario cava durante el primer turno y ataca en el segundo.', 100, 10, 'ATAQUE', 'TIERRA', 80, NULL, 2, NULL, 0, NULL),
(0092, 'Tóxico', 'Envenena gravemente al objetivo y causa un daño mayor en cada turno.', 90, 10, 'ESTADO', 'VENENO', 0, 'GRAVEMENTE ENVENENADO', 1, NULL, 0, NULL),
(0093, 'Confusión', 'Débil ataque telequinético que puede causar confusión.', 100, 25, 'ATAQUE', 'PSIQUICO', 50, 'CONFUSO', 1, NULL, 0, NULL),
(0094, 'Psíquico', 'Fuerte ataque telequinético que puede reducir la Defensa Especial del objetivo.', 100, 10, 'ATAQUE', 'PSIQUICO', 90, NULL, 1, NULL, 0, NULL),
(0095, 'Hipnosis', 'Ataque hipnótico que hace dormir profundamente al objetivo.', 60, 20, 'ESTADO', 'PSIQUICO', 0, 'DORMIDO', 1, NULL, 0, NULL),
(0096, 'Meditación', 'El usuario reposa y medita para potenciar el Ataque.', 100, 40, 'MEJORA', 'PSIQUICO', 0, NULL, 1, 'ATAQUE+', 0, 1),
(0097, 'Agilidad', 'Relaja y aligera el cuerpo para aumentar mucho la Velocidad.', 100, 30, 'MEJORA', 'PSIQUICO', 0, NULL, 1, 'VELOCIDAD+', 0, 2),
(0098, 'Ataque Rápido', 'Ataca al objetivo a tal velocidad que es casi imperceptible. Este movimiento tiene prioridad alta.', 100, 30, 'ATAQUE', 'NORMAL', 40, NULL, 1, NULL, 0, NULL),
(0099, 'Furia', 'Al usarse, aumenta el Ataque del usuario cada vez que es golpeado.', 100, 20, 'ATAQUE', 'NORMAL', 20, NULL, 1, NULL, 0, NULL),
(0100, 'Teletransporte', 'Permite al usuario cambiarse por otro Pokémon del equipo, si lo hay. Si un Pokémon salvaje usa este movimiento, huye del combate.', 100, 20, 'ESTADO', 'PSIQUICO', 0, NULL, 1, NULL, 0, NULL),
(0101, 'Tinieblas', 'Produce un espejismo ante el objetivo, que pierde tantos PS como nivel tenga el usuario.', 100, 15, 'ATAQUE', 'FANTASMA', 0, NULL, 1, NULL, 0, NULL),
(0102, 'Mimético', 'Copia el último movimiento usado por el objetivo, y puede utilizarlo mientras esté en el combate.', 100, 10, 'ESTADO', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0103, 'Chirrido', 'Chillido agudo que reduce mucho la Defensa del objetivo.', 85, 40, 'ESTADO', 'NORMAL', 0, NULL, 1, 'DEFENSA-', 0, 2),
(0104, 'Doble Equipo', 'El usuario se mueve a velocidad vertiginosa y crea copias ilusorias de sí mismo, con lo que aumenta su Evasión al desconcentrar al rival.', 100, 15, 'MEJORA', 'NORMAL', 0, NULL, 1, 'EVASION+', 0, 1),
(0105, 'Recuperación', 'El usuario regenera sus células y recupera una cantidad de PS equivalente a la mitad de sus PS máximos.', 100, 5, 'ESTADO', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0106, 'Fortaleza', 'El usuario tensa su musculatura para aumentar la Defensa.', 100, 30, 'MEJORA', 'NORMAL', 0, NULL, 1, 'DEFENSA+', 0, 1),
(0107, 'Reducción', 'El usuario mengua para aumentar mucho la Evasión.', 100, 10, 'MEJORA', 'NORMAL', 0, NULL, 1, 'EVASION+', 0, 2),
(0108, 'Pantalla de Humo', 'Reduce la Precisión del objetivo con una nube de humo o tinta.', 100, 20, 'ESTADO', 'NORMAL', 0, NULL, 1, 'PRECISION-', 0, 1),
(0109, 'Rayo Confuso', 'Rayo siniestro que confunde al objetivo.', 100, 10, 'ESTADO', 'FANTASMA', 0, 'CONFUSO', 1, NULL, 0, NULL),
(0110, 'Refugio', 'El usuario se resguarda en su coraza, por lo que le aumenta la Defensa.', 100, 40, 'MEJORA', 'NORMAL', 0, NULL, 1, 'DEFENSA+', 0, 1),
(0111, 'Rizo Defensa', 'Se enrosca para ocultar sus puntos débiles y aumenta la Defensa.', 100, 40, 'MEJORA', 'NORMAL', 0, NULL, 1, 'DEFENSA+', 0, 1),
(0112, 'Barrera', 'Crea una barrera que aumenta mucho la Defensa.', 100, 20, 'MEJORA', 'PSIQUICO', 0, NULL, 1, 'DEFENSA+', 0, 2),
(0113, 'Pantalla de Luz', 'Barrera misteriosa que reduce durante cinco turnos el daño producido por los ataques especiales del rival.', 100, 30, 'MEJORA', 'PSIQUICO', 0, NULL, 5, NULL, 0, NULL),
(0114, 'Niebla', 'Neblina oscura que elimina los cambios en las características de todos los Pokémon en combate.', 100, 30, 'ESTADO', 'HIELO', 0, NULL, 5, NULL, 0, NULL),
(0115, 'Reflejo', 'Barrera misteriosa que reduce durante cinco turnos el daño producido por los ataques físicos del rival.', 100, 20, 'MEJORA', 'PSIQUICO', 0, NULL, 5, NULL, 0, NULL),
(0116, 'Foco Energía', 'Concentra energía para aumentar las posibilidades de asestar un golpe crítico.', 100, 30, 'MEJORA', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0117, 'Venganza', 'Espera dos turnos para atacar con el doble de potencia del daño recibido.', 100, 10, 'ATAQUE', 'NORMAL', 0, NULL, 2, NULL, 0, NULL),
(0118, 'Metrónomo', 'Mueve un dedo y estimula su cerebro para usar al azar casi cualquier movimiento.', 100, 10, 'ESTADO', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0119, 'Espejo', 'Ataca al objetivo con el último movimiento que este haya usado.', 0, 20, 'ATAQUE', 'VOLADOR', 0, NULL, 1, NULL, 0, NULL),
(0120, 'Autodestrucción', 'El atacante explota y hiere a los Pokémon adyacentes. El usuario se debilita de inmediato.', 100, 5, 'ATAQUE', 'NORMAL', 200, NULL, 1, NULL, 0, NULL),
(0121, 'Bomba Huevo', 'Arroja un huevo enorme al objetivo con gran fuerza.', 75, 10, 'ATAQUE', 'NORMAL', 100, NULL, 1, NULL, 0, NULL),
(0122, 'Lengüetazo', 'El usuario ataca al objetivo lamiéndolo con su larga lengua. Puede causar parálisis.', 100, 30, 'ATAQUE', 'FANTASMA', 30, 'PARALIZADO', 1, NULL, 0, NULL),
(0123, 'Polución', 'Lanza un ataque con gases tóxicos que pueden llegar a envenenar.', 70, 20, 'ATAQUE', 'VENENO', 30, 'ENVENENADO', 1, NULL, 0, NULL),
(0124, 'Residuos', 'Arroja residuos al objetivo. Puede llegar a envenenar.', 100, 20, 'ATAQUE', 'VENENO', 65, 'ENVENENADO', 1, NULL, 0, NULL),
(0125, 'Hueso Palo', 'Aporrea con un hueso. Puede hacer retroceder al objetivo.', 85, 20, 'ATAQUE', 'TIERRA', 65, NULL, 1, NULL, 0, NULL),
(0126, 'Llamarada', 'Llama intensa que chamusca y puede causar quemaduras.', 85, 5, 'ATAQUE', 'FUEGO', 110, 'QUEMADO', 1, NULL, 0, NULL),
(0127, 'Cascada', 'Embiste con un gran impulso y puede llegar a amedrentar al objetivo.', 100, 15, 'ATAQUE', 'AGUA', 80, 'AMEDRENTADO', 1, NULL, 0, NULL),
(0128, 'Clamp', 'Atrapa y atenaza con fuerza durante cuatro o cinco turnos.', 85, 15, 'ATAQUE', 'AGUA', 35, 'ATRAPADO', 4, NULL, 0, NULL),
(0129, 'Rayo Estrella', 'Lanza rayos en forma de estrella que no fallan nunca.', 100, 20, 'ATAQUE', 'NORMAL', 60, NULL, 1, NULL, 0, NULL),
(0130, 'Cabezazo', 'El usuario se prepara y sube su Defensa en el primer turno y en el segundo arremete con un cabezazo.', 100, 10, 'ATAQUE', 'NORMAL', 130, NULL, 2, NULL, 0, NULL),
(0131, 'Clavo Cañón', 'Enquista los sueños de un objetivo dormido. El usuario recupera la mitad de los PS del daño que produce.', 100, 15, 'ATAQUE', 'PSIQUICO', 0, NULL, 1, NULL, 0, NULL),
(0132, 'Restricción', 'Ataca con largos tentáculos o zarcillos que pueden bajar la Velocidad.', 100, 35, 'ATAQUE', 'NORMAL', 10, NULL, 1, NULL, 0, NULL),
(0133, 'Amnesia', 'El usuario olvida sus preocupaciones y aumenta mucho la Defensa Especial.', 100, 20, 'MEJORA', 'PSIQUICO', 0, NULL, 1, 'DEFENSA ESPECIAL+', 0, 2),
(0134, 'Kinético', 'Debilita una cuchara para distraer al objetivo y reducir su Precisión.', 80, 15, 'ESTADO', 'PSIQUICO', 0, NULL, 1, 'PRECISION-', 0, 1),
(0135, 'Ovocuración', 'Restaura la mitad de los PS máximos del usuario.', 100, 5, 'ESTADO', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0136, 'Patada Salto Alta', 'El usuario salta muy alto y propina un rodillazo al objetivo. Si falla, se hiere a sí mismo.', 90, 10, 'ATAQUE', 'LUCHA', 130, NULL, 1, NULL, 0, NULL),
(0137, 'Deslumbrar', 'Intimida y asusta al objetivo con la mirada para dejarlo paralizado.', 75, 30, 'ESTADO', 'NORMAL', 0, 'PARALIZADO', 1, NULL, 0, NULL),
(0138, 'Comesueños', 'Enquile los sueños de un objetivo dormido. El usuario recupera la mitad de los PS del daño que produce.', 100, 15, 'ATAQUE', 'PSIQUICO', 100, NULL, 1, NULL, 0, NULL),
(0139, 'Gas Venenoso', 'Lanza una nube de gas tóxico al rostro del objetivo. Produce envenenamiento.', 90, 40, 'ESTADO', 'VENENO', 0, 'ENVENENADO', 1, NULL, 0, NULL),
(0140, 'Bombardeo', 'Arroja esferas al objetivo entre dos y cinco veces seguidas.', 85, 20, 'ATAQUE', 'NORMAL', 15, NULL, 1, NULL, 0, NULL),
(0141, 'Chupavidas', 'Chupa sangre al objetivo. El usuario recupera la mitad de los PS del daño que produce.', 100, 10, 'ATAQUE', 'BICHO', 80, NULL, 1, NULL, 0, NULL),
(0142, 'Beso Amoroso', 'Intimida al objetivo con una cara que asusta y le da un beso que lo deja dormido.', 75, 10, 'ESTADO', 'NORMAL', 0, 'DORMIDO', 1, NULL, 0, NULL),
(0143, 'Ataque Aéreo', 'Ataca en el segundo turno y suele asestar un golpe crítico. También puede amedrentar al objetivo.', 90, 5, 'ATAQUE', 'VOLADOR', 140, 'AMEDRENTADO', 2, NULL, 0, NULL),
(0144, 'Transformación', 'El usuario se transforma en una copia del objetivo, con los mismos movimientos.', 100, 10, 'ESTADO', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0145, 'Burbuja', 'Lanza burbujas a los contrincantes y puede reducir su Velocidad.', 100, 30, 'ATAQUE', 'AGUA', 40, NULL, 1, NULL, 0, NULL),
(0146, 'Puño Mareo', 'Rítmicos puñetazos que pueden causar confusión.', 100, 10, 'ATAQUE', 'NORMAL', 70, 'CONFUSO', 1, NULL, 0, NULL),
(0147, 'Espora', 'Esparce esporas que inducen el sueño.', 100, 15, 'ESTADO', 'PLANTA', 0, 'DORMIDO', 1, NULL, 0, NULL),
(0148, 'Destello', 'Luz cegadora que baja la Precisión del objetivo.', 100, 20, 'ESTADO', 'NORMAL', 0, NULL, 1, 'PRECISION-', 0, 1),
(0149, 'Psicoonda', 'Ataca con una onda de energía de intensidad variable.', 100, 15, 'ATAQUE', 'PSIQUICO', 0, NULL, 1, NULL, 0, NULL),
(0150, 'Salpicadura', 'No tiene ningún efecto. Solo salpica.', 100, 40, 'ESTADO', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0151, 'Armadura Ácida', 'Transforma su estructura celular para hacerse líquido y aumentar mucho la Defensa.', 100, 20, 'MEJORA', 'VENENO', 0, NULL, 1, 'DEFENSA+', 0, 2),
(0152, 'Martillazo', 'Golpea con fuerza con una pinza enorme. Suele asestar un golpe crítico.', 90, 10, 'ATAQUE', 'AGUA', 100, NULL, 1, NULL, 0, NULL),
(0153, 'Explosión', 'El atacante causa una grandísima explosión y hiere a los Pokémon adyacentes. El usuario se debilita de inmediato.', 100, 5, 'ATAQUE', 'NORMAL', 250, NULL, 1, NULL, 0, NULL),
(0154, 'Golpes Furia', 'Araña al objetivo con uñas, cuchillas o similares de dos a cinco veces seguidas.', 80, 15, 'ATAQUE', 'NORMAL', 18, NULL, 1, NULL, 0, NULL),
(0155, 'Huesomerang', 'Lanza el hueso que lleva a modo de bumerán contra el objetivo y le inflige daño dos veces seguidas.', 90, 10, 'ATAQUE', 'TIERRA', 50, NULL, 1, NULL, 0, NULL),
(0156, 'Descanso', 'Restaura todos los PS y cura todos los problemas de estado del usuario, que se duerme los dos turnos siguientes.', 100, 10, 'ESTADO', 'PSIQUICO', 0, NULL, 2, NULL, 0, NULL),
(0157, 'Avalancha', 'Lanza grandes pedruscos. Puede amedrentar al objetivo.', 90, 10, 'ATAQUE', 'ROCA', 75, 'AMEDRENTADO', 1, NULL, 0, NULL),
(0158, 'Hipercolmillo', 'Ataca con agudos colmillos. Puede amedrentar al objetivo.', 90, 15, 'ATAQUE', 'NORMAL', 80, 'AMEDRENTADO', 1, NULL, 0, NULL),
(0159, 'Afilar', 'El perfil del usuario se hace más afilado y su Ataque mejora.', 100, 30, 'MEJORA', 'NORMAL', 0, NULL, 1, 'ATAQUE+', 0, 1),
(0160, 'Conversión', 'Cambia el tipo del usuario por el del primer movimiento en su lista.', 100, 30, 'MEJORA', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0161, 'Triataque', 'Ataca con tres rayos de luz que puede paralizar, quemar o congelar al objetivo.', 100, 10, 'ATAQUE', 'NORMAL', 80, NULL, 1, NULL, 0, NULL),
(0162, 'Superdiente', 'Asesta una dentellada con sus afilados incisivos que reduce a la mitad los PS del objetivo.', 90, 10, 'ATAQUE', 'NORMAL', 50, NULL, 1, NULL, 0, NULL),
(0163, 'Cuchillada', 'Ataca con cuchillas o con pinzas. Suele asestar un golpe crítico.', 100, 20, 'ATAQUE', 'NORMAL', 70, NULL, 1, NULL, 0, NULL),
(0164, 'Sustituto', 'Utiliza parte de los PS propios para crear un sustituto que actúa como señuelo.', 0, 10, 'ESTADO', 'NORMAL', 0, NULL, 1, NULL, 0, NULL),
(0165, 'Forcejeo', 'Solo se usa como último recurso al acabarse los PP. El usuario se hiere ligeramente a sí mismo.', 100, 1, 'ATAQUE', 'NORMAL', 50, NULL, 1, NULL, 0, NULL);


-- Método INSERT con todas las rutas de imágenes, sprites y sonidos
INSERT INTO POKEDEX (NUM_POKEDEX, NOM_POKEMON, IMG_FRONTAL, IMG_TRASERA, SPRITES_FRONTAL, SPRITES_TRASEROS, SONIDO, NIVEL_EVOLUCION, TIPO1, TIPO2) VALUES
(1, 'BULBASAUR', '1.png', '1.png', '1.gif', '1.gif', 'cries_pokemon_legacy_1.ogg', 1, 'PLANTA', 'VENENO'),
(2, 'IVYSAUR', '2.png', '2.png', '2.gif', '2.gif', 'cries_pokemon_legacy_2.ogg', 2, 'PLANTA', 'VENENO'),
(3, 'VENUSAUR', '3.png', '3.png', '3.gif', '3.gif', 'cries_pokemon_legacy_3.ogg', 3, 'PLANTA', 'VENENO'),
(4, 'CHARMANDER', '4.png', '4.png', '4.gif', '4.gif', 'cries_pokemon_legacy_4.ogg', 1, 'FUEGO', NULL),
(5, 'CHARMELEON', '5.png', '5.png', '5.gif', '5.gif', 'cries_pokemon_legacy_5.ogg', 2, 'FUEGO', NULL),
(6, 'CHARIZARD', '6.png', '6.png', '6.gif', '6.gif', 'cries_pokemon_legacy_6.ogg', 3, 'FUEGO', 'VOLADOR'),
(7, 'SQUIRTLE', '7.png', '7.png', '7.gif', '7.gif', 'cries_pokemon_legacy_7.ogg', 1, 'AGUA', NULL),
(8, 'WARTORTLE', '8.png', '8.png', '8.gif', '8.gif', 'cries_pokemon_legacy_8.ogg', 2, 'AGUA', NULL),
(9, 'BLASTOISE', '9.png', '9.png', '9.gif', '9.gif', 'cries_pokemon_legacy_9.ogg', 3, 'AGUA', NULL),
(10, 'CATERPIE', '10.png', '10.png', '10.gif', '10.gif', 'cries_pokemon_legacy_10.ogg', 1, 'BICHO', NULL),
(11, 'METAPOD', '11.png', '11.png', '11.gif', '11.gif', 'cries_pokemon_legacy_11.ogg', 2, 'BICHO', NULL),
(12, 'BUTTERFREE', '12.png', '12.png', '12.gif', '12.gif', 'cries_pokemon_legacy_12.ogg', 3, 'BICHO', 'VOLADOR'),
(13, 'WEEDLE', '13.png', '13.png', '13.gif', '13.gif', 'cries_pokemon_legacy_13.ogg', 1, 'BICHO', 'VENENO'),
(14, 'KAKUNA', '14.png', '14.png', '14.gif', '14.gif', 'cries_pokemon_legacy_14.ogg', 2, 'BICHO', 'VENENO'),
(15, 'BEEDRILL', '15.png', '15.png', '15.gif', '15.gif', 'cries_pokemon_legacy_15.ogg', 3, 'BICHO', 'VENENO'),
(16, 'PIDGEY', '16.png', '16.png', '16.gif', '16.gif', 'cries_pokemon_legacy_16.ogg', 1, 'NORMAL', 'VOLADOR'),
(17, 'PIDGEOTTO', '17.png', '17.png', '17.gif', '17.gif', 'cries_pokemon_legacy_17.ogg', 2, 'NORMAL', 'VOLADOR'),
(18, 'PIDGEOT', '18.png', '18.png', '18.gif', '18.gif', 'cries_pokemon_legacy_18.ogg', 3, 'NORMAL', 'VOLADOR'),
(19, 'RATTATA', '19.png', '19.png', '19.gif', '19.gif', 'cries_pokemon_legacy_19.ogg', 1, 'NORMAL', NULL),
(20, 'RATICATE', '20.png', '20.png', '20.gif', '20.gif', 'cries_pokemon_legacy_20.ogg', 2, 'NORMAL', NULL),
(21, 'SPEAROW', '21.png', '21.png', '21.gif', '21.gif', 'cries_pokemon_legacy_21.ogg', 1, 'NORMAL', 'VOLADOR'),
(22, 'FEAROW', '22.png', '22.png', '22.gif', '22.gif', 'cries_pokemon_legacy_22.ogg', 2, 'NORMAL', 'VOLADOR'),
(23, 'EKANS', '23.png', '23.png', '23.gif', '23.gif', 'cries_pokemon_legacy_23.ogg', 1, 'VENENO', NULL),
(24, 'ARBOK', '24.png', '24.png', '24.gif', '24.gif', 'cries_pokemon_legacy_24.ogg', 2, 'VENENO', NULL),
(25, 'PIKACHU', '25.png', '25.png', '25.gif', '25.gif', 'cries_pokemon_legacy_25.ogg', 1, 'ELÉCTRICO', NULL),
(26, 'RAICHU', '26.png', '26.png', '26.gif', '26.gif', 'cries_pokemon_legacy_26.ogg', 2, 'ELÉCTRICO', NULL),
(27, 'SANDSHREW', '27.png', '27.png', '27.gif', '27.gif', 'cries_pokemon_legacy_27.ogg', 1, 'TIERRA', NULL),
(28, 'SANDSLASH', '28.png', '28.png', '28.gif', '28.gif', 'cries_pokemon_legacy_28.ogg', 2, 'TIERRA', NULL),
(29, 'NIDORAN♀', '29.png', '29.png', '29.gif', '29.gif', 'cries_pokemon_legacy_29.ogg', 1, 'VENENO', NULL),
(30, 'NIDORINA', '30.png', '30.png', '30.gif', '30.gif', 'cries_pokemon_legacy_30.ogg', 2, 'VENENO', NULL),
(31, 'NIDOQUEEN', '31.png', '31.png', '31.gif', '31.gif', 'cries_pokemon_legacy_31.ogg', 3, 'VENENO', 'TIERRA'),
(32, 'NIDORAN♂', '32.png', '32.png', '32.gif', '32.gif', 'cries_pokemon_legacy_32.ogg', 1, 'VENENO', NULL),
(33, 'NIDORINO', '33.png', '33.png', '33.gif', '33.gif', 'cries_pokemon_legacy_33.ogg', 2, 'VENENO', NULL),
(34, 'NIDOKING', '34.png', '34.png', '34.gif', '34.gif', 'cries_pokemon_legacy_34.ogg', 3, 'VENENO', 'TIERRA'),
(35, 'CLEFAIRY', '35.png', '35.png', '35.gif', '35.gif', 'cries_pokemon_legacy_35.ogg', 1, 'NORMAL', NULL),
(36, 'CLEFABLE', '36.png', '36.png', '36.gif', '36.gif', 'cries_pokemon_legacy_36.ogg', 2, 'NORMAL', NULL),
(37, 'VULPIX', '37.png', '37.png', '37.gif', '37.gif', 'cries_pokemon_legacy_37.ogg', 1, 'FUEGO', NULL),
(38, 'NINETALES', '38.png', '38.png', '38.gif', '38.gif', 'cries_pokemon_legacy_38.ogg', 2, 'FUEGO', NULL),
(39, 'JIGGLYPUFF', '39.png', '39.png', '39.gif', '39.gif', 'cries_pokemon_legacy_39.ogg', 1, 'NORMAL', NULL),
(40, 'WIGGLYTUFF', '40.png', '40.png', '40.gif', '40.gif', 'cries_pokemon_legacy_40.ogg', 2, 'NORMAL', NULL),
(41, 'ZUBAT', '41.png', '41.png', '41.gif', '41.gif', 'cries_pokemon_legacy_41.ogg', 1, 'VENENO', 'VOLADOR'),
(42, 'GOLBAT', '42.png', '42.png', '42.gif', '42.gif', 'cries_pokemon_legacy_42.ogg', 2, 'VENENO', 'VOLADOR'),
(43, 'ODDISH', '43.png', '43.png', '43.gif', '43.gif', 'cries_pokemon_legacy_43.ogg', 1, 'PLANTA', 'VENENO'),
(44, 'GLOOM', '44.png', '44.png', '44.gif', '44.gif', 'cries_pokemon_legacy_44.ogg', 2, 'PLANTA', 'VENENO'),
(45, 'VILEPLUME', '45.png', '45.png', '45.gif', '45.gif', 'cries_pokemon_legacy_45.ogg', 3, 'PLANTA', 'VENENO'),
(46, 'PARAS', '46.png', '46.png', '46.gif', '46.gif', 'cries_pokemon_legacy_46.ogg', 1, 'BICHO', 'PLANTA'),
(47, 'PARASECT', '47.png', '47.png', '47.gif', '47.gif', 'cries_pokemon_legacy_47.ogg', 2, 'BICHO', 'PLANTA'),
(48, 'VENONAT', '48.png', '48.png', '48.gif', '48.gif', 'cries_pokemon_legacy_48.ogg', 1, 'BICHO', 'VENENO'),
(49, 'VENOMOTH', '49.png', '49.png', '49.gif', '49.gif', 'cries_pokemon_legacy_49.ogg', 2, 'BICHO', 'VENENO'),
(50, 'DIGLETT', '50.png', '50.png', '50.gif', '50.gif', 'cries_pokemon_legacy_50.ogg', 1, 'TIERRA', NULL),
(51, 'DUGTRIO', '51.png', '51.png', '51.gif', '51.gif', 'cries_pokemon_legacy_51.ogg', 2, 'TIERRA', NULL),
(52, 'MEOWTH', '52.png', '52.png', '52.gif', '52.gif', 'cries_pokemon_legacy_52.ogg', 1, 'NORMAL', NULL),
(53, 'PERSIAN', '53.png', '53.png', '53.gif', '53.gif', 'cries_pokemon_legacy_53.ogg', 2, 'NORMAL', NULL),
(54, 'PSYDUCK', '54.png', '54.png', '54.gif', '54.gif', 'cries_pokemon_legacy_54.ogg', 1, 'AGUA', NULL),
(55, 'GOLDUCK', '55.png', '55.png', '55.gif', '55.gif', 'cries_pokemon_legacy_55.ogg', 2, 'AGUA', NULL),
(56, 'MANKEY', '56.png', '56.png', '56.gif', '56.gif', 'cries_pokemon_legacy_56.ogg', 1, 'LUCHA', NULL),
(57, 'PRIMEAPE', '57.png', '57.png', '57.gif', '57.gif', 'cries_pokemon_legacy_57.ogg', 2, 'LUCHA', NULL),
(58, 'GROWLITHE', '58.png', '58.png', '58.gif', '58.gif', 'cries_pokemon_legacy_58.ogg', 1, 'FUEGO', NULL),
(59, 'ARCANINE', '59.png', '59.png', '59.gif', '59.gif', 'cries_pokemon_legacy_59.ogg', 2, 'FUEGO', NULL),
(60, 'POLIWAG', '60.png', '60.png', '60.gif', '60.gif', 'cries_pokemon_legacy_60.ogg', 1, 'AGUA', NULL),
(61, 'POLIWHIRL', '61.png', '61.png', '61.gif', '61.gif', 'cries_pokemon_legacy_61.ogg', 2, 'AGUA', NULL),
(62, 'POLIWRATH', '62.png', '62.png', '62.gif', '62.gif', 'cries_pokemon_legacy_62.ogg', 3, 'AGUA', 'LUCHA'),
(63, 'ABRA', '63.png', '63.png', '63.gif', '63.gif', 'cries_pokemon_legacy_63.ogg', 1, 'PSÍQUICO', NULL),
(64, 'KADABRA', '64.png', '64.png', '64.gif', '64.gif', 'cries_pokemon_legacy_64.ogg', 2, 'PSÍQUICO', NULL),
(65, 'ALAKAZAM', '65.png', '65.png', '65.gif', '65.gif', 'cries_pokemon_legacy_65.ogg', 3, 'PSÍQUICO', NULL),
(66, 'MACHOP', '66.png', '66.png', '66.gif', '66.gif', 'cries_pokemon_legacy_66.ogg', 1, 'LUCHA', NULL),
(67, 'MACHOKE', '67.png', '67.png', '67.gif', '67.gif', 'cries_pokemon_legacy_67.ogg', 2, 'LUCHA', NULL),
(68, 'MACHAMP', '68.png', '68.png', '68.gif', '68.gif', 'cries_pokemon_legacy_68.ogg', 3, 'LUCHA', NULL),
(69, 'BELLSPROUT', '69.png', '69.png', '69.gif', '69.gif', 'cries_pokemon_legacy_69.ogg', 1, 'PLANTA', 'VENENO'),
(70, 'WEEPINBELL', '70.png', '70.png', '70.gif', '70.gif', 'cries_pokemon_legacy_70.ogg', 2, 'PLANTA', 'VENENO'),
(71, 'VICTREEBEL', '71.png', '71.png', '71.gif', '71.gif', 'cries_pokemon_legacy_71.ogg', 3, 'PLANTA', 'VENENO'),
(72, 'TENTACOOL', '72.png', '72.png', '72.gif', '72.gif', 'cries_pokemon_legacy_72.ogg', 1, 'AGUA', 'VENENO'),
(73, 'TENTACRUEL', '73.png', '73.png', '73.gif', '73.gif', 'cries_pokemon_legacy_73.ogg', 2, 'AGUA', 'VENENO'),
(74, 'GEODUDE', '74.png', '74.png', '74.gif', '74.gif', 'cries_pokemon_legacy_74.ogg', 1, 'ROCA', 'TIERRA'),
(75, 'GRAVELER', '75.png', '75.png', '75.gif', '75.gif', 'cries_pokemon_legacy_75.ogg', 2, 'ROCA', 'TIERRA'),
(76, 'GOLEM', '76.png', '76.png', '76.gif', '76.gif', 'cries_pokemon_legacy_76.ogg', 3, 'ROCA', 'TIERRA'),
(77, 'PONYTA', '77.png', '77.png', '77.gif', '77.gif', 'cries_pokemon_legacy_77.ogg', 1, 'FUEGO', NULL),
(78, 'RAPIDASH', '78.png', '78.png', '78.gif', '78.gif', 'cries_pokemon_legacy_78.ogg', 2, 'FUEGO', NULL),
(79, 'SLOWPOKE', '79.png', '79.png', '79.gif', '79.gif', 'cries_pokemon_legacy_79.ogg', 1, 'AGUA', 'PSÍQUICO'),
(80, 'SLOWBRO', '80.png', '80.png', '80.gif', '80.gif', 'cries_pokemon_legacy_80.ogg', 2, 'AGUA', 'PSÍQUICO'),
(81, 'MAGNEMITE', '81.png', '81.png', '81.gif', '81.gif', 'cries_pokemon_legacy_81.ogg', 1, 'ELÉCTRICO', NULL),
(82, 'MAGNETON', '82.png', '82.png', '82.gif', '82.gif', 'cries_pokemon_legacy_82.ogg', 2, 'ELÉCTRICO', NULL),
(83, 'FARFETCH’D', '83.png', '83.png', '83.gif', '83.gif', 'cries_pokemon_legacy_83.ogg', 1, 'NORMAL', 'VOLADOR'),
(84, 'DODUO', '84.png', '84.png', '84.gif', '84.gif', 'cries_pokemon_legacy_84.ogg', 1, 'NORMAL', 'VOLADOR'),
(85, 'DODRIO', '85.png', '85.png', '85.gif', '85.gif', 'cries_pokemon_legacy_85.ogg', 2, 'NORMAL', 'VOLADOR'),
(86, 'SEEL', '86.png', '86.png', '86.gif', '86.gif', 'cries_pokemon_legacy_86.ogg', 1, 'AGUA', NULL),
(87, 'DEWGONG', '87.png', '87.png', '87.gif', '87.gif', 'cries_pokemon_legacy_87.ogg', 2, 'AGUA', 'HIELO'),
(88, 'GRIMER', '88.png', '88.png', '88.gif', '88.gif', 'cries_pokemon_legacy_88.ogg', 1, 'VENENO', NULL),
(89, 'MUK', '89.png', '89.png', '89.gif', '89.gif', 'cries_pokemon_legacy_89.ogg', 2, 'VENENO', NULL),
(90, 'SHELLDER', '90.png', '90.png', '90.gif', '90.gif', 'cries_pokemon_legacy_90.ogg', 1, 'AGUA', NULL),
(91, 'CLOYSTER', '91.png', '91.png', '91.gif', '91.gif', 'cries_pokemon_legacy_91.ogg', 2, 'AGUA', 'HIELO'),
(92, 'GASTLY', '92.png', '92.png', '92.gif', '92.gif', 'cries_pokemon_legacy_92.ogg', 1, 'FANTASMA', 'VENENO'),
(93, 'HAUNTER', '93.png', '93.png', '93.gif', '93.gif', 'cries_pokemon_legacy_93.ogg', 2, 'FANTASMA', 'VENENO'),
(94, 'GENGAR', '94.png', '94.png', '94.gif', '94.gif', 'cries_pokemon_legacy_94.ogg', 3, 'FANTASMA', 'VENENO'),
(95, 'ONIX', '95.png', '95.png', '95.gif', '95.gif', 'cries_pokemon_legacy_95.ogg', 1, 'ROCA', 'TIERRA'),
(96, 'DROWZEE', '96.png', '96.png', '96.gif', '96.gif', 'cries_pokemon_legacy_96.ogg', 1, 'PSÍQUICO', NULL),
(97, 'HYPNO', '97.png', '97.png', '97.gif', '97.gif', 'cries_pokemon_legacy_97.ogg', 2, 'PSÍQUICO', NULL),
(98, 'KRABBY', '98.png', '98.png', '98.gif', '98.gif', 'cries_pokemon_legacy_98.ogg', 1, 'AGUA', NULL),
(99, 'KINGLER', '99.png', '99.png', '99.gif', '99.gif', 'cries_pokemon_legacy_99.ogg', 2, 'AGUA', NULL),
(100, 'VOLTORB', '100.png', '100.png', '100.gif', '100.gif', 'cries_pokemon_legacy_100.ogg', 1, 'ELÉCTRICO', NULL),
(101, 'ELECTRODE', '101.png', '101.png', '101.gif', '101.gif', 'cries_pokemon_legacy_101.ogg', 2, 'ELÉCTRICO', NULL),
(102, 'EXEGGCUTE', '102.png', '102.png', '102.gif', '102.gif', 'cries_pokemon_legacy_102.ogg', 1, 'PLANTA', 'PSÍQUICO'),
(103, 'EXEGGUTOR', '103.png', '103.png', '103.gif', '103.gif', 'cries_pokemon_legacy_103.ogg', 2, 'PLANTA', 'PSÍQUICO'),
(104, 'CUBONE', '104.png', '104.png', '104.gif', '104.gif', 'cries_pokemon_legacy_104.ogg', 1, 'TIERRA', NULL),
(105, 'MAROWAK', '105.png', '105.png', '105.gif', '105.gif', 'cries_pokemon_legacy_105.ogg', 2, 'TIERRA', NULL),
(106, 'HITMONLEE', '106.png', '106.png', '106.gif', '106.gif', 'cries_pokemon_legacy_106.ogg', 1, 'LUCHA', NULL),
(107, 'HITMONCHAN', '107.png', '107.png', '107.gif', '107.gif', 'cries_pokemon_legacy_107.ogg', 1, 'LUCHA', NULL),
(108, 'LICKITUNG', '108.png', '108.png', '108.gif', '108.gif', 'cries_pokemon_legacy_108.ogg', 1, 'NORMAL', NULL),
(109, 'KOFFING', '109.png', '109.png', '109.gif', '109.gif', 'cries_pokemon_legacy_109.ogg', 1, 'VENENO', NULL),
(110, 'WEEZING', '110.png', '110.png', '110.gif', '110.gif', 'cries_pokemon_legacy_110.ogg', 2, 'VENENO', NULL),
(111, 'RHYHORN', '111.png', '111.png', '111.gif', '111.gif', 'cries_pokemon_legacy_111.ogg', 1, 'TIERRA', 'ROCA'),
(112, 'RHYDON', '112.png', '112.png', '112.gif', '112.gif', 'cries_pokemon_legacy_112.ogg', 2, 'TIERRA', 'ROCA'),
(113, 'CHANSEY', '113.png', '113.png', '113.gif', '113.gif', 'cries_pokemon_legacy_113.ogg', 1, 'NORMAL', NULL),
(114, 'TANGELA', '114.png', '114.png', '114.gif', '114.gif', 'cries_pokemon_legacy_114.ogg', 1, 'PLANTA', NULL),
(115, 'KANGASKHAN', '115.png', '115.png', '115.gif', '115.gif', 'cries_pokemon_legacy_115.ogg', 1, 'NORMAL', NULL),
(116, 'HORSEA', '116.png', '116.png', '116.gif', '116.gif', 'cries_pokemon_legacy_116.ogg', 1, 'AGUA', NULL),
(117, 'SEADRA', '117.png', '117.png', '117.gif', '117.gif', 'cries_pokemon_legacy_117.ogg', 2, 'AGUA', NULL),
(118, 'GOLDEEN', '118.png', '118.png', '118.gif', '118.gif', 'cries_pokemon_legacy_118.ogg', 1, 'AGUA', NULL),
(119, 'SEAKING', '119.png', '119.png', '119.gif', '119.gif', 'cries_pokemon_legacy_119.ogg', 2, 'AGUA', NULL),
(120, 'STARYU', '120.png', '120.png', '120.gif', '120.gif', 'cries_pokemon_legacy_120.ogg', 1, 'AGUA', NULL),
(121, 'STARMIE', '121.png', '121.png', '121.gif', '121.gif', 'cries_pokemon_legacy_121.ogg', 2, 'AGUA', 'PSÍQUICO'),
(122, 'MR. MIME', '122.png', '122.png', '122.gif', '122.gif', 'cries_pokemon_legacy_122.ogg', 1, 'PSÍQUICO', 'HADA'),
(123, 'SCYTHER', '123.png', '123.png', '123.gif', '123.gif', 'cries_pokemon_legacy_123.ogg', 1, 'BICHO', 'VOLADOR'),
(124, 'JYNX', '124.png', '124.png', '124.gif', '124.gif', 'cries_pokemon_legacy_124.ogg', 1, 'HIELO', 'PSÍQUICO'),
(125, 'ELECTABUZZ', '125.png', '125.png', '125.gif', '125.gif', 'cries_pokemon_legacy_125.ogg', 1, 'ELÉCTRICO', NULL),
(126, 'MAGMAR', '126.png', '126.png', '126.gif', '126.gif', 'cries_pokemon_legacy_126.ogg', 1, 'FUEGO', NULL),
(127, 'PINSIR', '127.png', '127.png', '127.gif', '127.gif', 'cries_pokemon_legacy_127.ogg', 1, 'BICHO', NULL),
(128, 'TAUROS', '128.png', '128.png', '128.gif', '128.gif', 'cries_pokemon_legacy_128.ogg', 1, 'NORMAL', NULL),
(129, 'MAGIKARP', '129.png', '129.png', '129.gif', '129.gif', 'cries_pokemon_legacy_129.ogg', 1, 'AGUA', NULL),
(130, 'GYARADOS', '130.png', '130.png', '130.gif', '130.gif', 'cries_pokemon_legacy_130.ogg', 2, 'AGUA', 'VOLADOR'),
(131, 'LAPRAS', '131.png', '131.png', '131.gif', '131.gif', 'cries_pokemon_legacy_131.ogg', 1, 'AGUA', 'HIELO'),
(132, 'DITTO', '132.png', '132.png', '132.gif', '132.gif', 'cries_pokemon_legacy_132.ogg', 1, 'NORMAL', NULL),
(133, 'EEVEE', '133.png', '133.png', '133.gif', '133.gif', 'cries_pokemon_legacy_133.ogg', 1, 'NORMAL', NULL),
(134, 'VAPOREON', '134.png', '134.png', '134.gif', '134.gif', 'cries_pokemon_legacy_134.ogg', 2, 'AGUA', NULL),
(135, 'JOLTEON', '135.png', '135.png', '135.gif', '135.gif', 'cries_pokemon_legacy_135.ogg', 2, 'ELÉCTRICO', NULL),
(136, 'FLAREON', '136.png', '136.png', '136.gif', '136.gif', 'cries_pokemon_legacy_136.ogg', 2, 'FUEGO', NULL),
(137, 'PORYGON', '137.png', '137.png', '137.gif', '137.gif', 'cries_pokemon_legacy_137.ogg', 1, 'NORMAL', NULL),
(138, 'OMANYTE', '138.png', '138.png', '138.gif', '138.gif', 'cries_pokemon_legacy_138.ogg', 1, 'ROCA', 'AGUA'),
(139, 'OMASTAR', '139.png', '139.png', '139.gif', '139.gif', 'cries_pokemon_legacy_139.ogg', 2, 'ROCA', 'AGUA'),
(140, 'KABUTO', '140.png', '140.png', '140.gif', '140.gif', 'cries_pokemon_legacy_140.ogg', 1, 'ROCA', 'AGUA'),
(141, 'KABUTOPS', '141.png', '141.png', '141.gif', '141.gif', 'cries_pokemon_legacy_141.ogg', 2, 'ROCA', 'AGUA'),
(142, 'AERODACTYL', '142.png', '142.png', '142.gif', '142.gif', 'cries_pokemon_legacy_142.ogg', 1, 'ROCA', 'VOLADOR'),
(143, 'SNORLAX', '143.png', '143.png', '143.gif', '143.gif', 'cries_pokemon_legacy_143.ogg', 1, 'NORMAL', NULL),
(144, 'ARTICUNO', '144.png', '144.png', '144.gif', '144.gif', 'cries_pokemon_legacy_144.ogg', 1, 'HIELO', 'VOLADOR'),
(145, 'ZAPDOS', '145.png', '145.png', '145.gif', '145.gif', 'cries_pokemon_legacy_145.ogg', 1, 'ELÉCTRICO', 'VOLADOR'),
(146, 'MOLTRES', '146.png', '146.png', '146.gif', '146.gif', 'cries_pokemon_legacy_146.ogg', 1, 'FUEGO', 'VOLADOR'),
(147, 'DRATINI', '147.png', '147.png', '147.gif', '147.gif', 'cries_pokemon_legacy_147.ogg', 1, 'DRAGÓN', NULL),
(148, 'DRAGONAIR', '148.png', '148.png', '148.gif', '148.gif', 'cries_pokemon_legacy_148.ogg', 2, 'DRAGÓN', NULL),
(149, 'DRAGONITE', '149.png', '149.png', '149.gif', '149.gif', 'cries_pokemon_legacy_149.ogg', 3, 'DRAGÓN', 'VOLADOR'),
(150, 'MEWTWO', '150.png', '150.png', '150.gif', '150.gif', 'cries_pokemon_legacy_150.ogg', 1, 'PSÍQUICO', NULL),
(151, 'MEW', '151.png', '151.png', '151.gif', '151.gif', 'cries_pokemon_legacy_151.ogg', 1, 'PSÍQUICO', NULL);

-- Insert OBJETO
INSERT INTO OBJETO( ID_OBJETO , NOM_OBJETO , ATAQUE , DEFENSA ,AT_ESPECIAL , DEF_ESPECIAL , VELOCIDAD , VITALIDAD , PP , PRECIO) VALUES 
(000, 'Ninguno', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0'),
(001, 'Pesa', 20 , 20 , 0 , 0 , -20 , 0 , 0 , 200),
(002, 'Pluma', 0 , -20 , 0 , -20 , 30 , 0 , 0 , 500),
(003, 'Chaleco', -15 , 20 , 0 , 20 , -15 , 0 , 0 , 1000),
(004, 'Bastón', 0 , 0 , 0 , 20 , -15 , 0 , 20 , 750),
(005, 'Pilas', 0 , 0 , 0 , -30 , 0 , 0 , 50 , 100),
(006, 'Eter', 0 , 0 , 0 , 0 , 0 , 0 , 40 , 300),
(007, 'Anillo unico', 10 , 0 , 0 , 0 , 0 , 0 , 0 , 5000),
(008, 'Pokeball', 0 , 0 , 0 , 0 , 0 , 0 , 0 , 50),
(009, 'Pocion', 0 , 0 , 0 , 0 , 0 , 30 , 0 , 600),
(010, 'Superpocion', 0 , 0 , 0 , 0 , 0 , 60 , 0 , 800),
(011, 'Hiperpocion', 0 , 0 , 0 , 0 , 0 , 90 , 0 , 2000);

-- Cambio en la tabla objeto (Cambios realizados dia 23/04/2025)
ALTER TABLE OBJETO ADD RUTA_IMAGEN VARCHAR(100);
UPDATE OBJETO SET RUTA_IMAGEN = 'pesa.png' WHERE ID_OBJETO = 1;
UPDATE OBJETO SET RUTA_IMAGEN = 'pluma.png' WHERE ID_OBJETO = 2;
UPDATE OBJETO SET RUTA_IMAGEN = 'chaleco.png' WHERE ID_OBJETO = 3;
UPDATE OBJETO SET RUTA_IMAGEN = 'baston.png' WHERE ID_OBJETO = 4;
UPDATE OBJETO SET RUTA_IMAGEN = 'pila.png' WHERE ID_OBJETO = 5;
UPDATE OBJETO SET RUTA_IMAGEN = 'eter.png' WHERE ID_OBJETO = 6;
UPDATE OBJETO SET RUTA_IMAGEN = 'anillo.png' WHERE ID_OBJETO = 7;
UPDATE OBJETO SET RUTA_IMAGEN = 'pokeball.png' WHERE ID_OBJETO = 8;
UPDATE OBJETO SET RUTA_IMAGEN = 'pocion.png' WHERE ID_OBJETO = 9;
UPDATE OBJETO SET RUTA_IMAGEN = 'superpocion.png' WHERE ID_OBJETO = 10;
UPDATE OBJETO SET RUTA_IMAGEN = 'hiperpocion.png' WHERE ID_OBJETO = 11;

-- Cambio en la tabla objeto para insertar descripcion para cada uno de ellos (Cambios realizados dia 23/04/2025)
ALTER TABLE OBJETO ADD COLUMN DESCRIPCION VARCHAR(1000);

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Un objeto de entrenamiento extremo. Al equiparlo, el Pokémon se vuelve más fuerte y resistente, pero su velocidad se ve comprometida por el peso adicional. Ideal para luchadores que no necesitan moverse rápido.', 
  CHAR(10), CHAR(10),
  'Ataque +20%', CHAR(10),
  'Defensa +20%', CHAR(10),
  'Velocidad -20%'
) WHERE ID_OBJETO = 1;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Ligera como el viento, esta pluma mística permite que el Pokémon se mueva con increíble rapidez, aunque sacrifica parte de su resistencia al impacto.', 
  CHAR(10), CHAR(10),
  'Velocidad +30%', CHAR(10),
  'Defensa -20%', CHAR(10),
  'Defensa Especial -20%'
) WHERE ID_OBJETO = 2;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Un chaleco reforzado que protege al portador de ataques físicos y especiales. Sin embargo, su peso afecta tanto el movimiento como la fuerza bruta del Pokémon.', 
  CHAR(10), CHAR(10),
  'Defensa +20%', CHAR(10),
  'Defensa Especial +20%', CHAR(10),
  'Velocidad -15%', CHAR(10),
  'Ataque -15%'
) WHERE ID_OBJETO = 3;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Un bastón mágico que canaliza energía vital hacia el cuerpo del Pokémon, aumentando su resistencia general. A cambio, su movilidad se ve afectada.', 
  CHAR(10), CHAR(10),
  'Estamina +20%', CHAR(10),
  'Defensa +20%', CHAR(10),
  'Velocidad -15%'
) WHERE ID_OBJETO = 4;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Unas pilas de alta energía diseñadas para restaurar rápidamente la estamina. Perfectas para combates prolongados, aunque pueden dejar expuesta la defensa especial del Pokémon.', 
  CHAR(10), CHAR(10),
  'Recuperación de Estamina +50%', CHAR(10),
  'Defensa Especial -30%'
) WHERE ID_OBJETO = 5;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Un frasco con un líquido brillante que reenergiza los movimientos de un Pokémon. Solo se puede usar una vez por combate.', 
  CHAR(10), CHAR(10),
  'Restaura los PP de un movimiento'
) WHERE ID_OBJETO = 6;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Un anillo legendario que otorga poder absoluto durante un corto periodo. El Pokémon se vuelve invulnerable y su poder de ataque se multiplica exponencialmente. Su uso es extremadamente raro.', 
  CHAR(10), CHAR(10),
  'Invulnerable durante 3 turnos', CHAR(10),
  'Ataque ×10 durante esos turnos'
) WHERE ID_OBJETO = 7;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'La herramienta clásica de todo entrenador. Usada para capturar Pokémon salvajes. Un básico imprescindible para cualquier aventura.', 
  CHAR(10), CHAR(10),
  'Precio: 50 Pokédólares'
) WHERE ID_OBJETO = 8;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Una poción básica que restaura un poco la salud de tu Pokémon. Ideal para combates tempranos o entrenamientos rápidos.', 
  CHAR(10), CHAR(10),
  'Cura 30 PS'
) WHERE ID_OBJETO = 9;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Una versión mejorada de la poción estándar. Restablece una cantidad moderada de salud, útil para momentos más tensos del combate.', 
  CHAR(10), CHAR(10),
  'Cura 60 PS'
) WHERE ID_OBJETO = 10;

UPDATE OBJETO SET DESCRIPCION = CONCAT(
  'Una potente medicina que devuelve una gran cantidad de salud al instante. Esencial para enfrentamientos difíciles o Pokémon con muchos PS.', 
  CHAR(10), CHAR(10),
  'Cura 90 PS'
) WHERE ID_OBJETO = 11;

-- Cambios realizados dia 25/04/2025
ALTER TABLE POKEMON
ADD COLUMN VITALIDADMAX INT(11);

UPDATE POKEMON
SET VITALIDADMAX = VITALIDAD;

UPDATE POKEMON
SET VITALIDADMAX_OBJ = VITALIDADMAX;

-- Cambios realizador 26/04/2025
ALTER TABLE mochila ADD RUTA_IMAGEN VARCHAR(100);
UPDATE OBJETO SET RUTA_IMAGEN = 'pesa.png' WHERE ID_OBJETO = 1;
UPDATE OBJETO SET RUTA_IMAGEN = 'pluma.png' WHERE ID_OBJETO = 2;
UPDATE OBJETO SET RUTA_IMAGEN = 'chaleco.png' WHERE ID_OBJETO = 3;
UPDATE OBJETO SET RUTA_IMAGEN = 'baston.png' WHERE ID_OBJETO = 4;
UPDATE OBJETO SET RUTA_IMAGEN = 'pila.png' WHERE ID_OBJETO = 5;
UPDATE OBJETO SET RUTA_IMAGEN = 'eter.png' WHERE ID_OBJETO = 6;
UPDATE OBJETO SET RUTA_IMAGEN = 'anillo.png' WHERE ID_OBJETO = 7;
UPDATE OBJETO SET RUTA_IMAGEN = 'pokeball.png' WHERE ID_OBJETO = 8;
UPDATE OBJETO SET RUTA_IMAGEN = 'pocion.png' WHERE ID_OBJETO = 9;
UPDATE OBJETO SET RUTA_IMAGEN = 'superpocion.png' WHERE ID_OBJETO = 10;
UPDATE OBJETO SET RUTA_IMAGEN = 'hiperpocion.png' WHERE ID_OBJETO = 11;