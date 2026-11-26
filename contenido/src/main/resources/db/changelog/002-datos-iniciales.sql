--liquibase formatted sql

--changeset creamy:002
INSERT INTO blogs (id, titulo, resumen, categoria, fecha, autor, minutos, imagen, alt) VALUES
(1, 'Cómo elegir la torta perfecta para cada celebración', 'Cumpleaños, aniversarios o una once con amigos: te contamos qué sabor y tamaño conviene en cada ocasión.', 'Guías', '2026-10-15', 'Equipo Creamy', 4, '/images/comida/pastel_frutilla.jpg', 'Torta de crema y frutilla'),
(2, 'Café y pastel: combinaciones que funcionan', 'Un espresso corta lo dulce, un latte acompaña lo cremoso. Aprende a combinar tu bebida con el postre.', 'Café', '2026-09-28', 'Equipo Creamy', 3, '/images/bebestible/espresso.jpg', 'Taza de espresso'),
(3, 'Alergias en pastelería: cómo leer los ingredientes', 'Frutos secos, lácteos, huevo y gluten están presentes en muchas preparaciones. Te explicamos qué revisar antes de comprar.', 'Consejos', '2026-09-10', 'Equipo Creamy', 5, '/images/comida/pastel_zana.jpg', 'Torta de zanahoria'),
(4, 'Tres ideas para que tu brownie quede húmedo', 'La textura lo es todo. Estos consejos sirven tanto para hornear en casa como para elegir uno bien hecho.', 'Consejos', '2026-08-22', 'Equipo Creamy', 3, '/images/comida/brownie.jpg', 'Brownie de chocolate'),
(5, 'Cómo conservar tus pasteles en casa', 'Refrigerador, tiempo y envase: lo básico para que tu torta llegue en buen estado al día siguiente.', 'Consejos', '2026-08-05', 'Equipo Creamy', 4, '/images/comida/tres_leches.jpg', 'Torta tres leches'),
(6, 'Postres chilenos que no pueden faltar en tu mesa', 'Del mil hojas al chilenito de manjar: un recorrido por los dulces que nos recuerdan la once en casa.', 'Tradición', '2026-07-14', 'Equipo Creamy', 4, '/images/comida/mil_hojas.jpg', 'Mil hojas con manjar');

INSERT INTO blog_parrafos (blog_id, orden, texto) VALUES
(1, 0, 'Elegir una torta empieza por pensar en quiénes van a comerla. Para un cumpleaños con niños, los sabores clásicos como la crema con frutilla o el chocolate rara vez fallan. Para una reunión de adultos, una tres leches o una torta de zanahoria suelen ser una buena apuesta porque no resultan tan dulces.'),
(1, 1, 'El tamaño también importa. Como referencia, una porción generosa alcanza para una persona, pero si habrá más postres en la mesa puedes calcular porciones más pequeñas y evitar que sobre demasiado.'),
(1, 2, 'Por último, consulta si alguno de los invitados tiene alergias o restricciones. En cada producto de nuestra tienda encontrarás el detalle de ingredientes para que puedas decidir con tranquilidad.'),
(2, 0, 'La regla general es buscar contraste o armonía. Un espresso, con su sabor intenso y sin leche, equilibra postres muy dulces o cremosos, como un cheesecake o un mil hojas con manjar.'),
(2, 1, 'Si prefieres armonía, un latte de vainilla o de caramelo acompaña bien bizcochos suaves y rollitos. Y para los amantes del chocolate, un moka junto a un brownie es una combinación pensada para quien disfruta del sabor intenso.'),
(2, 2, 'No hay reglas estrictas: prueba distintas combinaciones y quédate con la que más te guste.'),
(3, 0, 'En pastelería, ingredientes como el huevo, la leche, la harina de trigo y los frutos secos aparecen en muchísimas recetas. Por eso, antes de elegir un producto, lo más seguro es revisar el listado completo de ingredientes.'),
(3, 1, 'En nuestra tienda, al abrir el detalle de cada producto puedes ver sus ingredientes. Un ejemplo: la torta de zanahoria lleva nueces, algo importante para quienes tienen alergia a los frutos secos.'),
(3, 2, 'Si tienes una alergia severa, te recomendamos contactarnos antes de hacer tu pedido para resolver cualquier duda.'),
(4, 0, 'Primero, no lo hornees de más. Un brownie debe sacarse del horno cuando el centro todavía está ligeramente blando, porque sigue cocinándose con el calor residual.'),
(4, 1, 'Segundo, usa chocolate de buena calidad. Un chocolate con buen porcentaje de cacao aporta sabor y una textura más densa.'),
(4, 2, 'Tercero, déjalo reposar antes de cortarlo. Si lo cortas muy caliente se desarma; con unos minutos de reposo queda firme por fuera y húmedo por dentro.'),
(5, 0, 'Los pasteles con crema, como la tres leches o la de crema y frutilla, deben guardarse siempre en el refrigerador, en un recipiente tapado para que no absorban olores de otros alimentos.'),
(5, 1, 'Antes de servir, sácalos unos minutos para que recuperen su textura y su sabor. Una torta recién salida del frío se siente más dura y con menos aroma.'),
(5, 2, 'Para galletas y brownies, un envase hermético a temperatura ambiente suele ser suficiente por uno o dos días.'),
(6, 0, 'El manjar es protagonista de muchos de nuestros dulces favoritos. Está en el mil hojas, entre capas finas y crujientes de masa, y en los chilenitos, esas galletas suaves que se rellenan y se espolvorean con azúcar flor.'),
(6, 1, 'El kuchen de manzana, heredero de la tradición de pastelería del sur del país, es otro infaltable en una buena once, sobre todo con un café caliente.'),
(6, 2, 'Todos ellos funcionan igual de bien para una reunión familiar que para un regalo.');