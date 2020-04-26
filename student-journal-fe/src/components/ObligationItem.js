import React, { Component } from 'react';
import PropType from 'prop-types';
import Card from '@material-ui/core/Card'
import Typography from '@material-ui/core/Typography'
import CssBaseline from '@material-ui/core/CssBaseline';

import Grid from '@material-ui/core/Grid'




export class ObligationItem extends Component {
    render() {
        console.log(this.props.obligat)
        const { date, description, courseName, type } = this.props.obligat;

        return (
            <React.Fragment>
                <CssBaseline />
                <Grid item>
                    <Card raised={true}>
                        <Grid container justify='center' direction='row'>
                            <Grid item>
                                <Grid container justify='center' direction='column'>
                                    <Grid item>
                                        <Typography color='primary' variant='caption' variant="h6" gutterBottom>
                                            Predmet
                                        </Typography>
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="body2" gutterBottom>
                                            {courseName}
                                        </Typography>
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="h6" gutterBottom>
                                            Datum i vreme
                    </Typography>
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="body2" gutterBottom>
                                            {date}
                                        </Typography>
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="h6" gutterBottom>
                                            Tip
                    </Typography>
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="body2" gutterBottom>
                                            {type}
                                        </Typography>
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="h6" gutterBottom>
                                            Opis
                                        </Typography>
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="body2" gutterBottom>
                                            {description}
                                        </Typography>
                                    </Grid>

                                </Grid>
                            </Grid>
                        </Grid>
                    </Card>
                </Grid>
            </React.Fragment>
        )
    }
}

ObligationItem.propType = {
    obligation: PropType.object.isRequired
}



export default ObligationItem
