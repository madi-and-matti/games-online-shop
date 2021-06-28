import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IGame, defaultValue } from 'app/shared/model/game.model';

export const ACTION_TYPES = {
  FETCH_GAMES: 'games/FETCH_GAMES',
  RESET: 'userManagement/RESET',
  FETCH_SINGLE_GAME: 'games/FETCH_SINGLE_GAME',
};

const initialState = {
  loading: false,
  errorMessage: null,
  games: [] as ReadonlyArray<IGame>,
  totalItems: 0,
  singleGame: defaultValue,
};

export type GamesState = Readonly<typeof initialState>;

// Reducer
export default (state: GamesState = initialState, action): GamesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMES):
      return {
        ...state,
        errorMessage: null,
        loading: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMES):
      return {
        ...state,
        loading: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMES):
      return {
        ...state,
        loading: false,
        games: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case REQUEST(ACTION_TYPES.FETCH_SINGLE_GAME):
      return {
        ...state,
        errorMessage: null,
        loading: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SINGLE_GAME):
      return {
        ...state,
        loading: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SINGLE_GAME):
      return {
        ...state,
        loading: false,
        singleGame: action.payload.data,
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/games';
// Actions
export const getGames: ICrudGetAllAction<IGame> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMES,
    payload: axios.get<IGame>(requestUrl),
  };
};

export const getSingleGame: ICrudGetAction<IGame> = (id: string) => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SINGLE_GAME,
    payload: axios.get<IGame>(requestUrl),
  };
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
