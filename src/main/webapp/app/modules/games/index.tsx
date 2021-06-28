import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import AllGames from './all-games';
import SingleGame from "./single-game";

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SingleGame}/>
    <ErrorBoundaryRoute exact path={`${match.url}/`} component={AllGames} />
  </div>
);

export default Routes;
