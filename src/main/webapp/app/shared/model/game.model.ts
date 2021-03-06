export interface IGame {
  id?: string;
  name?: string;
  description?: IDescription;
  price?: number;
  yearPublished?: number;
  minimumPlayers?: number;
  maximumPlayers?: number;
  minimumAge?: number;
  smallImageUrl?: string;
  largeImageUrl?: string;
  userRating?: number;
  rulesUrl?: string;
  quantity?: number;
  status?: string;
  mechanics?: any[];
  categories?: any[];
}

interface IDescription {
  id?: number;
  text?: string;
}

export const defaultValue: Readonly<IGame> = {
  id: '',
  name: '',
  description: {
    id: 0,
    text: '',
  },
  price: 0,
  yearPublished: 0,
  minimumPlayers: 0,
  maximumPlayers: 0,
  minimumAge: 0,
  smallImageUrl: '',
  largeImageUrl: '',
  userRating: 0,
  rulesUrl: '',
  quantity: 0,
  status: '',
  mechanics: [],
  categories: [],
};
