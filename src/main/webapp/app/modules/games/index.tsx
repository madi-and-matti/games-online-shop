import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import AllGames from './games-all';
import SingleGame from "./single-game/single-game";

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SingleGame}/>
    <ErrorBoundaryRoute exact path={`${match.url}/`} component={AllGames} />
  </div>
);

export default Routes;
