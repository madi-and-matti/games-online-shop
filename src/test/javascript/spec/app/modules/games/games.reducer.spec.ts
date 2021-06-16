import configureStore from 'redux-mock-store';
import promiseMiddleware from 'redux-promise-middleware';
import axios from 'axios';
import thunk from 'redux-thunk';
import sinon from 'sinon';

import { REQUEST, FAILURE, SUCCESS } from 'app/shared/reducers/action-type.util';

import games, { ACTION_TYPES, getGames, reset } from 'app/modules/games/games.reducer';
import { defaultValue, IGame } from 'app/shared/model/game.model';

describe('Games reducer tests', () => {
  function isEmpty(element): boolean {
    if (element instanceof Array) {
      return element.length === 0;
    } else {
      return Object.keys(element).length === 0;
    }
  }

  function testMultipleTypes(types, payload, testFunction) {
    types.forEach(e => {
      testFunction(games(undefined, { type: e, payload }));
    });
  }

  describe('Common tests', () => {
    it('should return the initial state', () => {
      const toTest = games(undefined, {});
      // TODO: Add to this object as initial state expands
      expect(toTest).toMatchObject({
        loading: false,
        errorMessage: null,
        totalItems: 0,
      });
      expect(isEmpty(toTest.games));
    });
  });

  describe('Requests', () => {
    it('should set state to loading', () => {
      // TODO: Add new action types as they are created
      testMultipleTypes([REQUEST(ACTION_TYPES.FETCH_GAMES)], {}, state => {
        expect(state).toMatchObject({
          errorMessage: null,
          loading: true,
        });
      });
    });
  });

  describe('Failures', () => {
    it('should set state to failed and put an error message in errorMessage', () => {
      // TODO: Add new action types as they are created
      testMultipleTypes([FAILURE(ACTION_TYPES.FETCH_GAMES)], 'something happened', state => {
        expect(state).toMatchObject({
          loading: false,
          errorMessage: 'something happened',
        });
      });
    });
  });

  describe('Success', () => {
    it('should update state according to a successful fetch games request', () => {
      const headers = { ['x-total-count']: 42 };
      const payload = { data: 'some games', headers };
      const toTest = games(undefined, { type: SUCCESS(ACTION_TYPES.FETCH_GAMES), payload });

      expect(toTest).toMatchObject({
        loading: false,
        games: payload.data,
        totalItems: headers['x-total-count'],
      });
    });
  });

  describe('Reset', () => {
    it('should reset the state', () => {
      // TODO: Add to this object as initial state grows
      const initialState = {
        loading: false,
        errorMessage: null,
        games: [] as ReadonlyArray<IGame>,
        totalItems: 0,
        singleGame: defaultValue,
      };
      const payload = {
        ...initialState,
        loading: true,
      };
      expect(
        games(payload, {
          type: ACTION_TYPES.RESET,
        })
      ).toEqual(initialState);
    });
  });

  describe('Actions', () => {
    let store;

    const resolvedObject = { value: 'whatever' };
    beforeEach(() => {
      const mockStore = configureStore([thunk, promiseMiddleware]);
      store = mockStore({});
      axios.get = sinon.stub().returns(Promise.resolve(resolvedObject));
    });

    it('dispatches FETCH_GAMES_PENDING and FETCH_GAMES_FULFILLED actions', async () => {
      const expectedActions = [
        {
          type: REQUEST(ACTION_TYPES.FETCH_GAMES),
        },
        {
          type: SUCCESS(ACTION_TYPES.FETCH_GAMES),
          payload: resolvedObject,
        },
      ];
      await store.dispatch(getGames()).then(() => expect(store.getActions()).toEqual(expectedActions));
    });
    it('dispatches FETCH_GAMES_PENDING and FETCH_GAMES_FULFILLED actions with pagination options', async () => {
      const expectedActions = [
        {
          type: REQUEST(ACTION_TYPES.FETCH_GAMES),
        },
        {
          type: SUCCESS(ACTION_TYPES.FETCH_GAMES),
          payload: resolvedObject,
        },
      ];
      await store.dispatch(getGames(1, 20, 'id,desc')).then(() => expect(store.getActions()).toEqual(expectedActions));
    });
    it('dispatches ACTION_TYPES.RESET actions', async () => {
      const expectedActions = [
        {
          type: ACTION_TYPES.RESET,
        },
      ];
      await store.dispatch(reset());
      expect(store.getActions()).toEqual(expectedActions);
    });
  });
});
