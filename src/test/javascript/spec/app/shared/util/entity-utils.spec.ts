import { cleanEntity, getSortStateForAllGames, mapIdList, overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

describe('Entity utils', () => {
  describe('cleanEntity', () => {
    it('should not remove fields with an id', () => {
      const entityA = {
        a: {
          id: 5,
        },
      };
      const entityB = {
        a: {
          id: '5',
        },
      };

      expect(cleanEntity({ ...entityA })).toEqual(entityA);
      expect(cleanEntity({ ...entityB })).toEqual(entityB);
    });

    it('should remove fields with an empty id', () => {
      const entity = {
        a: {
          id: '',
        },
      };

      expect(cleanEntity({ ...entity })).toEqual({});
    });

    it('should not remove fields that are not objects', () => {
      const entity = {
        a: '',
        b: 5,
        c: [],
        d: '5',
      };

      expect(cleanEntity({ ...entity })).toEqual(entity);
    });
  });

  describe('mapIdList', () => {
    it("should map ids no matter the element's type", () => {
      const ids = ['jhipster', '', 1, { key: 'value' }];

      expect(mapIdList(ids)).toEqual([{ id: 'jhipster' }, { id: 1 }, { id: { key: 'value' } }]);
    });

    it('should return an empty array', () => {
      const ids = [];

      expect(mapIdList(ids)).toEqual([]);
    });
  });

  describe('overridePaginationStateWithQueryParams', () => {
    it('should parse sort params passed in locationSearch', () => {
      const paginationBaseState = {
        itemsPerPage: 24,
        sort: '',
        order: '',
        activePage: 0,
      };
      const locationSearch = '?page=1&sort=id,desc';

      expect(overridePaginationStateWithQueryParams(paginationBaseState, locationSearch)).toEqual({
        itemsPerPage: 24,
        sort: 'id',
        order: 'desc',
        activePage: 1,
      });
    });

    it('should return pagination base state if sort params not present in locationSearch', () => {
      const paginationBaseState = {
        itemsPerPage: 24,
        sort: 'userRating',
        order: 'desc',
        activePage: 1,
      };

      expect(overridePaginationStateWithQueryParams(paginationBaseState, '')).toEqual(paginationBaseState);
    });
  });

  describe('getSortStateForAllGames', () => {
    it('gets page, sort, and order from location params', () => {
      const search = '?page=3&sort=id,asc';
      const paginationBaseState = getSortStateForAllGames({ search }, 24);

      expect(paginationBaseState.itemsPerPage).toEqual(24);
      expect(paginationBaseState.sort).toEqual('id');
      expect(paginationBaseState.order).toEqual('asc');
      expect(paginationBaseState.activePage).toEqual(3);
    });

    it('returns userRating, desc, page1 when no params in location', () => {
      const paginationBaseState = getSortStateForAllGames({ search: '' }, 24);

      expect(paginationBaseState.itemsPerPage).toEqual(24);
      expect(paginationBaseState.sort).toEqual('userRating');
      expect(paginationBaseState.order).toEqual('desc');
      expect(paginationBaseState.activePage).toEqual(1);
    });
  });
});
