import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Col, Container, Row } from 'reactstrap';
import { getSortState } from 'react-jhipster';

import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import {getSortStateForAllGames, overridePaginationStateWithQueryParams} from 'app/shared/util/entity-utils';
import { getGames } from "app/modules/games/games.reducer";
import { IRootState } from 'app/shared/reducers';
import GamesSortDropdown from "app/modules/games/games-sort-dropdown";
import GamesAllCard from "app/modules/games/games-all-card";
import Pagination from "app/shared/layout/pagination/pagination";

export interface IAllGamesProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

const GamesAll = (props: IAllGamesProps) =>  {
  const [pagination, setPagination] = useState(
    overridePaginationStateWithQueryParams(getSortStateForAllGames(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  useEffect(() => {
    props.getGames(pagination.activePage - 1, pagination.itemsPerPage, `${pagination.sort},${pagination.order}`);
    const endURL = `?page=${pagination.activePage}&sort=${pagination.sort},${pagination.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  }, [pagination.activePage, pagination.order, pagination.sort]);

  const handlePagination = currentPage =>
    setPagination({
      ...pagination,
      activePage: currentPage,
    });

  const handleSort = (sort, order) =>
    setPagination({
      ...pagination,
      activePage: 1,
      sort,
      order,
    });

  const {games, totalItems} = props;
  return (
    <div className="all-games-container">
      <Row className="justify-content-end">
        <GamesSortDropdown handleSort={handleSort}/>
      </Row>
      <Container fluid={true}>
        <Row>
          {games.map((game, i) => (
            <Col key={`game-${i}`} xs="12" sm="4" lg="3">
              <Link to={`/games/${game.id}`} className="link-component">
                <GamesAllCard game={game}/>
              </Link>
            </Col>
          ))}
        </Row>
      </Container>
      <Pagination pagination={pagination} handlePagination={handlePagination} items={games} totalItems={totalItems} />
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  games: storeState.games.games,
  totalItems: storeState.games.totalItems,
});

const mapDispatchToProps = { getGames };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GamesAll);
