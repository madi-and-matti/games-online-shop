import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavDropdown } from './menu-components';

const gamesMenuItems = (
  <>
    <MenuItem icon="bell" to="/games">
      All Games
    </MenuItem>
  </>
);

export const GamesMenu = props => (
  <NavDropdown icon="th-list" name="Games" id="games-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    {gamesMenuItems}
  </NavDropdown>
);

export default GamesMenu;
