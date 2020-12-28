const axios = require('axios');
const fs = require('fs');
const faker = require('faker');
const bcrypt = require('bcrypt');

const BASE_URL = "https://api.boardgameatlas.com/api/";
const CLIENT_ID_QUERY_PARAM = "?client_id=pX8HrechSB";

const getCategories = async () => {
  try {
    return await axios.get(BASE_URL + "game/categories" + CLIENT_ID_QUERY_PARAM);
  } catch (e) {
    console.error(e)
  }
};

const getMechanics = async () => {
  try {
    return await axios.get(BASE_URL + "game/mechanics" + CLIENT_ID_QUERY_PARAM);
  } catch (e) {
    console.error(e)
  }
};

const getGames = async (skip, param) => {
  try {
    return await axios.get(`${BASE_URL}search${CLIENT_ID_QUERY_PARAM}&skip=${skip}&${param}`);
  } catch (e) {
    console.error(e);
  }
};

const seedAllGames = async () => {

  const { gameStream, descriptionStream, categoryStream, joinCategoryStream, mechanicsStream, joinMechanicsStream, orderItemStream } = createGameStreams();

  const ordersTotalPrices = createOrdersTotalPricesMap();

  const alreadySeeded = new Set();
  let descriptionId = 0;

  const categoryIds = new Set();
  const mechanicsIds = new Set();

  try {
    let response = await getCategories();
    const categories = response.data.categories;
    response = await getMechanics();
    const mechanics = response.data.mechanics;
    await populateIdsSets(categories, mechanics);

    // Seed by category
    for (const category of categories) {
      categoryStream.write(`${category.id};${category.name}\n`);
      for (let i = 0; i <= 1000; i += 100) {
        const { data } = await getGames(i, `categories=${category.id}`);
        const games = data.games;
        if (games) {
          createOrderItem(games[0]);
          games.forEach(game => seedGame(game));
          if (games.length < 100) {
            console.log(`categoryName=${category.name} categoryId=${category.id} currentSkip=${i} games.length=${games.length} alreadySeeded.size=${alreadySeeded.size}`);
            break;
          }
        } else {
          throw new Error("no games found");
        }
      }
    }

    // Seed by mechanics
    for (const mechanic of mechanics) {
      mechanicsStream.write(`${mechanic.id};${mechanic.name}\n`);
      for (let i = 0; i <= 1000; i += 100) {
        const { data } = await getGames(i, `mechanics=${mechanic.id}`);
        const games = data.games;
        if (games) {
          games.forEach(game => seedGame(game));
          if (games.length < 100) {
            console.log(`mechanicName=${mechanic.name} mechanicId=${mechanic.id} currentSkip=${i} games.length=${games.length} alreadySeeded.size=${alreadySeeded.size}`);
            break;
          }
        } else {
          throw new Error("no games found");
        }
      }
    }

    seedOrders();

  } catch (e) {
    console.error(e);
  } finally {
    gameStream.end();
    descriptionStream.end();
    categoryStream.end();
    joinCategoryStream.end();
    mechanicsStream.end();
    joinMechanicsStream.end();
    orderItemStream.end();
  }

  async function populateIdsSets(categories, mechanics) {
    try {
      for (const category in categories) {
        categoryIds.add(category.id);
      }
      for (const mechanic in mechanics) {
        mechanicsIds.add(mechanic.id);
      }
    } catch (e) {
      console.error(e);
    }

  }

  function seedGame(game) {
    const gameId = game.id;
    if (!alreadySeeded.has(gameId)) {
      const quantity = getQuantity(game.description);
      gameStream.write(`${gameId};"${game.name}";${game.year_published};${game.min_players};${game.max_players};${game.min_age};${Math.floor(game.price * 100)};${getStatus(quantity)};${game.images.small};${game.images.large};${game.average_user_rating};${game.rules_url};${quantity}\n`);
      descriptionStream.write(`${++descriptionId};${gameId};"${game.description.split("\r\n").join("<br />").split("\n").join("<br />")}"\n`);
      game.categories.forEach(cat => {
        if (categoryIds.has(cat.id)) {
          joinCategoryStream.write(`${gameId};${cat.id}\n`);
        }
      });
      game.mechanics.forEach(mec => {
        if (mechanicsIds.has(mec.id)) {
          joinMechanicsStream.write(`${gameId};${mec.id}\n`);
        }
      });
      alreadySeeded.add(gameId);
    }
  }

  function createOrderItem(game) {
    if (game && game.price > 0) {
      const orderId = Math.ceil(Math.random() * 50);
      const quantity = Math.ceil(Math.random() * 3);
      const price = Math.floor(game.price * 100);
      const totalCost = price * quantity;


      orderItemStream.write(`${orderId};${game.id};${price};${quantity}\n`);
      ordersTotalPrices[orderId] += totalCost;
    }
  }

  function seedOrders() {
    const orderStream = fs.createWriteStream("./data/order.csv", {flags: "w"});
    orderStream.write("id;user_id;status_id;price_total\n");

    for (let i = 1; i <= 50; i++) {
      if (ordersTotalPrices[i]) {
        const userId = Math.ceil(Math.random() * 1000);
        const statusId = getStatusId(i);
        orderStream.write(`${i};${userId};${statusId};${ordersTotalPrices[i]}\n`);
      }
    }

    orderStream.end();
  }

  function getStatusId(i) {
    const mod = i % 5;
    switch(mod) {
      case 1:
        return 1;
      case 2:
        return 2;
      case 3:
        return 3;
      case 4:
        return 4;
      default:
        return 5;
    }
  }
};

// UTILITY FUNCTIONS
function createGameStreams() {
  const gameStream = fs.createWriteStream("./data/game.csv", {flags: "w"});
  gameStream.write("id;name;year_published;min_players;max_players;min_age;price;status_id;small_img;large_img;user_rating;rules_url;quantity\n");

  const descriptionStream = fs.createWriteStream("./data/description.csv", {flags: "w"});
  descriptionStream.write("id;game_id;text\n");

  const categoryStream = fs.createWriteStream("./data/category.csv", {flags: "w"});
  categoryStream.write("id;name\n");

  const joinCategoryStream = fs.createWriteStream("./data/game_category.csv", {flags: "w"});
  joinCategoryStream.write("game_id;category_id\n");

  const mechanicsStream = fs.createWriteStream("./data/mechanics.csv", {flags: "w"});
  mechanicsStream.write("id;name\n");

  const joinMechanicsStream = fs.createWriteStream("./data/game_mechanics.csv", {flags: "w"});
  joinMechanicsStream.write("game_id;mechanics_id\n");

  const orderItemStream = fs.createWriteStream("./data/order_item.csv", {flags: "w"});
  orderItemStream.write("order_id;product_id;price;quantity\n");

  return {
    gameStream,
    descriptionStream,
    categoryStream,
    joinCategoryStream,
    mechanicsStream,
    joinMechanicsStream,
    orderItemStream
  }
}

function createOrdersTotalPricesMap() {
  const orderTotalPrices = {};

  for (let i = 1; i <= 50; i++) {
    orderTotalPrices[i] = 0;
  }
  return orderTotalPrices;
}

function getQuantity(description) {
  if (description === "") {
    return 0;
  }
  return Math.floor(Math.random() * Math.floor(41));
}

function getStatus(quantity) {
  if (quantity === 0) {
    // out of stock
    return 1;
  } else if (quantity < 20) {
    // running low
    return 3;
  } else {
    // in stock
    return 2;
  }
}


async function seedUsers() {
  const { userStream, userAuthStream, userAddressStream } = createUserStreams();

  const users = new Set();
  const addressIds = new Set();

  for (let i = 5; i <= 1000; i++) {
    const firstName = faker.name.firstName();
    let lastName = faker.name.lastName();
    let login = generateLogin(firstName, lastName);
    while (users.has(login)) {
      lastName = faker.name.lastName();
      login = generateLogin(firstName, lastName);
    }
    users.add(login);
    let hashedPassword = "";
    await bcrypt.hash(faker.internet.password(), 10).then(function(hash) {
      hashedPassword = hash;
    });
    // write to user csv
    userStream.write(`${i};${generateLogin(firstName, lastName)};${hashedPassword};${firstName};${lastName};${generateEmail(firstName, lastName)};;true;en;system;system\n`);

    // write to auth csv
    userAuthStream.write(`${i};ROLE_USER\n`);
    if (i % 100 === 0) {
      userAuthStream.write(`${i};ROLE_ADMIN\n`);
    }

    // write to address csv
    let addressId = Math.floor(Math.random() * 50000);
    while (addressIds.has(addressId)) {
      addressId = Math.floor(Math.random() * 50000);
    }
    addressIds.add(addressId);
    userAddressStream.write(`${addressId};${faker.address.streetAddress()};;${faker.address.city()};${Math.ceil(Math.random() * 10)};${faker.address.zipCode()};${i}\n`);
  }

  userStream.end();
  userAuthStream.end();
  userAddressStream.end();
}

function createUserStreams() {
  const userStream = fs.createWriteStream("./data/user.csv", {flags: "w"});
  const userAuthStream = fs.createWriteStream("./data/user_authority.csv", {flags: "w"});
  writeOriginalData(userStream, userAuthStream);

  const userAddressStream = fs.createWriteStream("./data/address.csv", {flags: "w"});
  userAddressStream.write("id;street_address;unit;city;state_id;zip_code;user_id\n");

  return {
    userStream,
    userAuthStream,
    userAddressStream
  }
}

function generateLogin(firstName, lastName) {
  return firstName.substring(0,1).concat(lastName).toLowerCase();
}

function generateEmail(firstName, lastName) {
  return `${firstName.toLowerCase()}.${lastName.toLowerCase()}@email.com`;
}

function writeOriginalData(userStream, userAuthStream) {
  userStream.write(
`id;login;password_hash;first_name;last_name;email;image_url;activated;lang_key;created_by;last_modified_by
1;system;$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG;System;System;system@localhost;;true;en;system;system
2;anonymoususer;$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO;Anonymous;User;anonymous@localhost;;true;en;system;system
3;admin;$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC;Administrator;Administrator;admin@localhost;;true;en;system;system
4;user;$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K;User;User;user@localhost;;true;en;system;system
`);

  userAuthStream.write(
    "user_id;authority_name\n" +
    "1;ROLE_ADMIN\n" +
    "1;ROLE_USER\n" +
    "2;ROLE_GUEST\n" +
    "3;ROLE_ADMIN\n" +
    "3;ROLE_USER\n" +
    "4;ROLE_USER\n"
  );
}

seedAllGames();
seedUsers();
