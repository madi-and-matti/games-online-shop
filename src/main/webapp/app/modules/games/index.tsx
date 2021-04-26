import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import AllGames from './all-games';

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute path={`${match.url}/`} component={AllGames} />
  </div>
);

export default Routes;
